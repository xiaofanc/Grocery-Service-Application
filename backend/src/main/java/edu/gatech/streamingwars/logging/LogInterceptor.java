package edu.gatech.streamingwars.logging;

import org.slf4j.MDC;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import java.util.UUID;

public class LogInterceptor implements HandlerInterceptor {
    private final static String UNIQUE_ID = "requestId";
    public static String requestId;


    @Override
    public void postHandle(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, ModelAndView arg3) throws Exception {
    }

    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = UUID.randomUUID().toString().replaceAll("-","");
        MDC.put(UNIQUE_ID, token);
        requestId = token;
        return true;
    }

    @Override
    public void afterCompletion(HttpServletRequest arg0, HttpServletResponse arg1, Object arg2, Exception arg3) throws Exception {
        MDC.remove(UNIQUE_ID);
    }

}