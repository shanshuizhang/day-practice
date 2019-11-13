package com.zss.mini.http.core.invoke;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;
import net.sf.cglib.proxy.MethodProxy;

import java.lang.reflect.Method;

/**
 * @author fuguozhang
 * @email fuguozhang@jyblife.com
 * @date 2019/11/4 18:06
 */
public class ControllerCglib implements MethodInterceptor {

    private Object target;

    public Object getTarget(Object object){
        this.target = object;
        Enhancer enhancer = new Enhancer();
        enhancer.setSuperclass(this.target.getClass());
        enhancer.setCallback(this);
        return enhancer.create();
    }
    @Override
    public Object intercept(Object o, Method method, Object[] objects, MethodProxy methodProxy) throws Throwable {
        preHandle();
        Object value = methodProxy.invokeSuper(o,objects);
        afterHandle();
        return value;
    }

    private void preHandle(){
    }

    private void afterHandle(){

    }

    public static void main(String[] args) {

    }
}
