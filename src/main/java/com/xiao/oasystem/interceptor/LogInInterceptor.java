package com.xiao.oasystem.interceptor;

import com.alibaba.fastjson.JSONObject;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.utils.JWTUtil;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.util.StringUtils;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

@Component
public class LogInInterceptor implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        if(!StringUtils.hasLength(token)) {
            Result<String> result = Result.fail(Result.NOT_ACCREDITED, "您的登录信息已过期", "FALSE");
            String s = JSONObject.toJSONString(result);
            response.getWriter().write(s);
            return false;
        }
        try{
            JWTUtil.parseJWT(token);
        }catch (Exception e){
            Result<String> result = Result.fail(Result.NOT_ACCREDITED, "您的登录信息已过期", "FALSE");
            String s = JSONObject.toJSONString(result);
            response.getWriter().write(s);
            return false;
        }
        return true;
    }

    @Override
    public void postHandle(HttpServletRequest request, HttpServletResponse response, Object handler, ModelAndView modelAndView) throws Exception {
        HandlerInterceptor.super.postHandle(request, response, handler, modelAndView);
    }

    @Override
    public void afterCompletion(HttpServletRequest request, HttpServletResponse response, Object handler, Exception ex) throws Exception {
        HandlerInterceptor.super.afterCompletion(request, response, handler, ex);
    }
}
