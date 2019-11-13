package com.zss.login.filter;

import com.zss.login.pojo.CurrentUser;
import com.zss.login.pojo.UserBO;
import com.zss.login.service.UserService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.redisson.api.RBucket;
import org.redisson.api.RedissonClient;
import org.springframework.beans.factory.annotation.Autowired;

import javax.servlet.*;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.IOException;
import java.io.PrintWriter;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/10/28 15:58
 */
@Slf4j
public abstract class KickOutFilter implements Filter {

    @Autowired
    public RedissonClient redissonClient;

    @Autowired
    public UserService userService;

    public static final String PREFIX = "uni_token_";

    public static final String PREFIX_LOCK = "uni_token_lock_";

    @Override
    public void doFilter(ServletRequest servletRequest, ServletResponse servletResponse, FilterChain filterChain) throws IOException, ServletException {
        HttpServletRequest request = (HttpServletRequest) servletRequest;
        HttpServletResponse response = (HttpServletResponse)servletResponse;

        try{
            if(checkToken(request,response) && isAccessAllowed(request,response)){
                filterChain.doFilter(servletRequest,servletResponse);
            }
        }catch (Exception e){
            log.error("过滤失败，异常信息：",e);
        }
    }

    @Override
    public void init(FilterConfig filterConfig) throws ServletException {

    }

    @Override
    public void destroy() {

    }

    /**
     * 当前用户是否允许访问
     * @param request
     * @param response
     * @return
     * @throws Exception
     */
    protected abstract boolean isAccessAllowed(HttpServletRequest request, HttpServletResponse response) throws Exception;

    /**
     * 检查是否携带token 以及token是否失效
     * @param request
     * @param response
     * @return
     */
    private boolean checkToken(HttpServletRequest request, HttpServletResponse response){
        log.info("checkToken 方法执行了");
        String token = request.getHeader("Authorization");
        if(StringUtils.isBlank(token)){
            sendJsonResponse(response,401,"你无权访问");
            return false;
        }

        RBucket<UserBO> rBucket = redissonClient.getBucket(token);
        UserBO userBO = rBucket.get();
        if(userBO == null){
            sendJsonResponse(response,403,"无效令牌");
            return false;
        }

        CurrentUser.put(userBO);
        return true;
    }

    public static void sendJsonResponse(HttpServletResponse response,int code,String message){
        sendJsonResponse(response,String.format(jsonTemplate(),code,message));
    }

    private static String jsonTemplate(){
        return "{\"code\":%s,\"msg\":\"%s\",\"data\":null,\"errors\":null}";
    }

    private static void sendJsonResponse(HttpServletResponse response,String json){
        response.setCharacterEncoding("UTF-8");
        response.setContentType("application/json; charset=utf-8");
        PrintWriter out = null;
        try{
            out = response.getWriter();
            out.append(json);
        }catch (Exception e){
            log.error("输出json结果失败，json信息：{}，异常信息：",json,e);
        }finally {
            if(out != null){
                out.close();
            }
        }
    }
}
