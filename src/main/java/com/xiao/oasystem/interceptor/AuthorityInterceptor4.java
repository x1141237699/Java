package com.xiao.oasystem.interceptor;

import cn.hutool.core.bean.BeanUtil;
import com.alibaba.fastjson.JSONObject;
import com.xiao.oasystem.pojo.VO.UserVO;
import com.xiao.oasystem.pojo.entity.User;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.springframework.stereotype.Component;
import org.springframework.web.servlet.HandlerInterceptor;
import org.springframework.web.servlet.ModelAndView;

//SUPER_MANAGER
@Component
public class AuthorityInterceptor4 implements HandlerInterceptor {
    @Override
    public boolean preHandle(HttpServletRequest request, HttpServletResponse response, Object handler) throws Exception {
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        if(User.SUPER_MANAGER > userVO.getPosition()) {
            Result<String> result = Result.fail(Result.NOT_ACCREDITED, "您的权限不足", "FALSE");
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
