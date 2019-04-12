/**
 *
 */
package com.magic.security.interceptor;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.core.NamedThreadLocal;
import org.springframework.stereotype.Component;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

/**
 * @author wang.bin
 *
 */
@Component
public class TimeInterceptor implements HandlerInterceptor {

    private static final Logger logger = LoggerFactory.getLogger(TimeInterceptor.class);
    private NamedThreadLocal<Long> methodInvokeTimeThreadLocal = new NamedThreadLocal<Long>("method_invoke_time");

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler)
            throws Exception {
        long beginTime = System.currentTimeMillis();// 开始时间
        methodInvokeTimeThreadLocal.set(beginTime);// 线程绑定变量（该数据只有当前请求的线程可见）
        return true;// 继续流程
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler,
                           ModelAndView modelAndView) throws Exception {
        consumerTime("postHandle",request,handler);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex)
            throws Exception {
        consumerTime("afterCompletion",request,handler);
    }


    private void consumerTime(String interceptionName,HttpServletRequest request,Object handler){
        long endTime = System.currentTimeMillis();// 结束时间
        long beginTime = methodInvokeTimeThreadLocal.get();// 得到线程绑定的局部变量（开始时间）
        long consumeTime = endTime - beginTime;// 消耗的时间

        // 判断请求路径的正确性
        if(handler instanceof HandlerMethod){
            String className =((HandlerMethod)handler).getBean().getClass().getSimpleName();
            String method = ((HandlerMethod)handler).getMethod().getName();

            logger.info("[{}->{}->{}->{}] consume {} millis",className,method,request.getRequestURI(),interceptionName,consumeTime);
        }

    }

}
