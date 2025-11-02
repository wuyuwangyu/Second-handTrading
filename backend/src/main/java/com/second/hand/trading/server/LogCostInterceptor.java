package com.second.hand.trading.server;

import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.util.Date;

	/**
	 *
	 *   拦截器
	 *
	 * */

// public class LogCostInterceptor implements HandlerInterceptor {
    // 这里有线程安全问题
    // private long start = System.currentTimeMillis();

    // @Override
    // public boolean preHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o) throws Exception {
    //     start = System.currentTimeMillis();
    //     return true;
    // }

    // @Override
    // public void postHandle(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, ModelAndView modelAndView) throws Exception {
    //     System.out.println("请求："+httpServletRequest.getRequestURI()+"?"+httpServletRequest.getQueryString()+"，耗时"+(System.currentTimeMillis()-start)+"ms  "+new Date());
    // }

    // @Override
    // public void afterCompletion(HttpServletRequest httpServletRequest, HttpServletResponse httpServletResponse, Object o, Exception e) throws Exception {
    // }
// }

public class LogCostInterceptor implements HandlerInterceptor {
    private static final Logger logger = LoggerFactory.getLogger(LogCostInterceptor.class);
    private static final String START_TIME_ATTRIBUTE = "startTime";

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        // 将开始时间存储在请求属性中，确保线程安全
        request.setAttribute(START_TIME_ATTRIBUTE, System.currentTimeMillis());
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        Long startTime = (Long) request.getAttribute(START_TIME_ATTRIBUTE);
        if (startTime != null) {
            long cost = System.currentTimeMillis() - startTime;
            String queryString = request.getQueryString();
            String requestUrl = request.getRequestURI() + (queryString != null ? "?" + queryString : "");
            
            logger.info("请求: {}，耗时: {}ms，时间: {}", requestUrl, cost, new Date());
        }
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        // 如果需要记录异常信息，可以在这里处理
        if (ex != null) {
            logger.error("请求处理异常: {}", request.getRequestURI(), ex);
        }
    }
}