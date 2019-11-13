package com.zss.mini.http.util;

import com.zss.mini.http.common.annotation.ControllerMapping;
import com.zss.mini.http.common.annotation.Filter;
import com.zss.mini.http.common.config.MyFrameConfig;
import com.zss.mini.http.common.constant.RequestConstants;
import com.zss.mini.http.common.em.PageMappingEnum;
import com.zss.mini.http.model.FilterModel;
import io.netty.util.internal.StringUtil;
import lombok.extern.slf4j.Slf4j;

import java.io.*;
import java.lang.reflect.Method;
import java.util.*;

import static com.zss.mini.http.util.JarFileUtil.readResources;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 16:36
 */
@Slf4j
public class CommonUtil {

    private static final int SIZE = 10240;

    /**
     * 截取url里面的路径字段信息
     *
     * @param uri
     * @return
     */
    public static String getUri(String uri) {
        int pathIndex = uri.indexOf("/");
        int requestIndex = uri.indexOf("?");
        String result;
        if (requestIndex < 0) {
            result = uri.trim().substring(pathIndex);
        } else {
            result = uri.trim().substring(pathIndex, requestIndex);
        }
        return result;
    }

    /**
     * 读取页面的html代码
     *
     * @return
     */
    @Deprecated
    public static String readPageHtml(int code) throws IOException {
        //获取文件内容
        File file = readFile(PageMappingEnum.getPath(code));
        return stream2String(new FileInputStream(file), "UTF-8");
    }

    /**
     * 读取初始化页面的html代码
     *
     * @return
     */
    public static String readIndexHtml(String indexPath) throws IOException {
        if (MyFrameConfig.INDEX_CHANGE) {
            return readPageHtmlInPath(indexPath);
        }
        //获取jar里面的index页面
        return new JarFileUtil().readJarFileIndex(MyFrameConfig.INDEX_PAGE);
    }

    /**
     * 读取初始化页面的html代码
     *
     * @return
     */
    public static String read404Html() throws IOException {
        if (MyFrameConfig.NOT_FOUND_CHANGE) {
            return readPageHtmlInPath(MyFrameConfig.NOT_FOUND_PAGE);
        }
        //获取jar里面固定的文件内容
        return new JarFileUtil().readJarFileIndex(MyFrameConfig.NOT_FOUND_PAGE);
    }

    /**
     * 可以直接读取resource底下的html
     *
     * @return
     */
    public static String readPageHtmlInPath(String path) {
        //获取文件内容
        File file = readFile(path);
        if (file == null) {
            return null;
        }
        try {
            return readResources(new FileInputStream(file));
        } catch (IOException e) {
            log.error("[CommonUtil] 文件读取异常，信息为{}", e);
            return StringUtil.EMPTY_STRING;
        }
    }

    /**
     * 读取resource文件夹底下的文件内容
     *
     * @param url
     * @return
     * @throws IOException
     */
    public static String readFileFromResource(String url) throws IOException {
        //获取文件内容
        File file = readFile(url);
        if (file == null) {
            return RequestConstants.PAGE_NOT_FOUND;
        }
        return readResources(new FileInputStream(file));
    }

    /**
     * 读取文件信息
     *
     * @return
     */
    public static File readFile(String path) {
        //获取文件内容
        if (path.startsWith("/")) {
            path = path.substring(path.indexOf("/") + 1);
        }
        File file = null;
        try {
            String paths = MyFrameConfig.APPLICATION_CLASS.getClassLoader().getResource(path).getFile();
            file = new File(paths);
        } catch (Exception e) {
            return null;
        }
        return file;
    }

    /**
     * 文件转换为字符串
     *
     * @param in      字节流
     * @param charset 文件的字符集
     * @return 文件内容
     */
    public static String stream2String(InputStream in, String charset) {
        StringBuffer sb = new StringBuffer();
        try {
            Reader r = new InputStreamReader(in, charset);
            int length = 0;
            for (char[] c = new char[SIZE]; (length = r.read(c)) != -1; ) {
                sb.append(c, 0, length);
            }
            r.close();
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        } catch (IOException e) {
            e.printStackTrace();
        }
        return sb.toString();
    }

    /**
     * 获取指定的函数方法
     *
     * @param targetMethod
     * @param name
     * @return
     */
    public static Method getMethodByName(Method[] targetMethod, String name) {
        for (Method method : targetMethod) {
            if (method.getName().equals(name)) {
                return method;
            }
        }
        return null;
    }

    /**
     * 判断其所实现的接口是否和预期一致
     *
     * @param target
     * @param interfaceClazz
     * @return
     */
    public static boolean isContaionInterFace(Object target, Class interfaceClazz) {
        Class<?>[] interfaceNames = target.getClass().getInterfaces();
        String expectName = interfaceClazz.getName();
        for (Class<?> interfaceName : interfaceNames) {
            if (interfaceName.getName().equals(expectName)) {
                return true;
            }
        }
        return false;
    }

    /**
     * 扫描控制器
     *
     * @param path
     * @return
     */
    public static Map<String, String> scanController(String path) {
        Map<String, String> result = new HashMap<>(60);
        Set<Class<?>> clazz = ClassUtil.getClzFromPackage(path);
        for (Class<?> aClass : clazz) {
            if (aClass.isAnnotationPresent(ControllerMapping.class)) {
                ControllerMapping controllerMapping = aClass.getAnnotation(ControllerMapping.class);
                String url = controllerMapping.url();
                result.put(url, aClass.getName());
                System.out.println("[Controller] " + aClass.getName());
            }
        }
        return result;
    }

    /**
     * 扫描过滤器
     *
     * @param path
     * @return
     */
    public static List<FilterModel> scanFilter(String path) throws IllegalAccessException, InstantiationException {
        Set<Class<?>> clazz = ClassUtil.getClzFromPackage(path);
        List<FilterModel> filterModelList = new ArrayList<>();
        for (Class<?> aClass : clazz) {
            if (aClass.isAnnotationPresent(Filter.class)) {
                Filter filter = aClass.getAnnotation(Filter.class);
                FilterModel filterModel = new FilterModel(filter.order(), filter.name(), aClass.newInstance());
                filterModelList.add(filterModel);
            }
        }
        FilterModel[] tempArr = filterModelList.stream().toArray(FilterModel[]::new);
        filterModelList.stream().forEach(filterModel -> log.info("FilterModel信息：{}",filterModel.toString()));
        return sortFilterModel(tempArr);
    }

    /**
     * 对加载的filter进行优先级排序
     *
     * @return
     */
    private static List<FilterModel> sortFilterModel(FilterModel[] filterModels) {
        for (int i = 0; i < filterModels.length; i++) {
            int minOrder = filterModels[i].getOrder();
            int minIndex = i;
            for (int j = i; j < filterModels.length; j++) {
                if (minOrder > filterModels[j].getOrder()) {
                    minOrder = filterModels[j].getOrder();
                    minIndex = j;
                }
            }
            FilterModel temp = filterModels[minIndex];
            filterModels[minIndex] = filterModels[i];
            filterModels[i] = temp;
        }
        return Arrays.asList(filterModels);
    }

    /**
     * 读取图片
     *
     * @param path
     * @return
     */
    public static RandomAccessFile readPic(String path) {
        RandomAccessFile file = null;
        try {
            file = new RandomAccessFile(readFile(path), "r");
        } catch (FileNotFoundException e) {
            e.printStackTrace();
        }
        return file;
    }


    public static void main(String[] args) throws InstantiationException, IllegalAccessException {
        List<FilterModel> filters = scanFilter("com.zss");
        for (FilterModel filter : filters) {
            System.out.println(filter.toString());
        }
    }
}
