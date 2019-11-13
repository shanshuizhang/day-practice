package com.zss.mini.http.core.handle;

import com.zss.mini.http.common.config.MyFrameConfig;
import com.zss.mini.http.common.constant.RequestConstants;
import com.zss.mini.http.model.CssModel;
import com.zss.mini.http.model.JsModel;
import com.zss.mini.http.model.PageModel;
import com.zss.mini.http.model.PicModel;
import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 17:13
 */
@Slf4j
public class ControllerReactor {

    public static List<ControllerMapping> CONTROLLER_LIST = new ArrayList<>();

    public static List<String> IMG_TYPE_LIST = Arrays.asList(".jpg",".png",".jpeg",".bmp",".gif");

    public static Object getClazzFromList(String uri){
        if("/".equals(uri) || "/index".equalsIgnoreCase(uri)){
            PageModel pageModel;
            if(MyFrameConfig.INDEX_CHANGE){
                pageModel = new PageModel();
                pageModel.setPagePath(MyFrameConfig.INDEX_PAGE);
            }
            return new PageModel();
        }
        if (uri.endsWith(RequestConstants.HTML_TYPE)) {
            return new PageModel(uri);
        }
        if (uri.endsWith(RequestConstants.JS_TYPE)) {
            return new JsModel(uri);
        }
        if (uri.endsWith(RequestConstants.CSS_TYPE)) {
            return new CssModel(uri);
        }
        if (isPicTypeMatch(uri)) {
            return new PicModel(uri);
        }
        Optional<ControllerMapping> cmOpt = CONTROLLER_LIST.stream().filter(cp -> cp.getUrl().equals(uri)).findFirst();
        if(cmOpt.isPresent()){
            String className = cmOpt.get().getClazz();
            try {
                Class clazz = Class.forName(className);
                Object object = clazz.newInstance();
                return object;
            } catch (ClassNotFoundException | IllegalAccessException | InstantiationException e) {
                log.error("[MockController] 类加载异常，{}",e);
            }
        }
        return null;
    }

    private static boolean isPicTypeMatch(String type){
        return IMG_TYPE_LIST.stream().anyMatch(curType -> type.endsWith(curType));
    }
}
