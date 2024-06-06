package com.xiao.oasystem.config;

import com.xiao.oasystem.interceptor.AuthorityInterceptor2;
import com.xiao.oasystem.interceptor.AuthorityInterceptor3;
import com.xiao.oasystem.interceptor.AuthorityInterceptor4;
import com.xiao.oasystem.interceptor.LogInInterceptor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.EnableWebMvc;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Configuration
public class WebConfig implements WebMvcConfigurer {

    @Autowired
    private LogInInterceptor logInInterceptor;

    @Autowired
    private AuthorityInterceptor2 authorityInterceptor2;

    @Autowired
    private AuthorityInterceptor3 authorityInterceptor3;

    @Autowired
    private AuthorityInterceptor4 authorityInterceptor4;

    //配置拦截器
    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        //登录校验
        registry.addInterceptor(logInInterceptor).addPathPatterns("/**").excludePathPatterns("/user/login");
        //GROUP_MANAGER权限校验
        registry.addInterceptor(authorityInterceptor2).addPathPatterns("/group/set/content", "/group/set/work/finished"
                                                                        , "/application/for/holiday/agree", "/application/for/holiday/refuse", "/application/for/holiday/reject"
                                                                        , "/application/for/group/agree", "/application/for/group/refuse");
        //DEPARTMENT_MANAGER权限校验
        registry.addInterceptor(authorityInterceptor3).addPathPatterns("/group/set", "/group/delete"
                                                                        , "/department/set/announcement"
                                                                        , "/application/for/department/agree", "/application/for/department/refuse");
        //SUPER_MANAGER权限校验
        registry.addInterceptor(authorityInterceptor4).addPathPatterns("/record/**", "/user/set", "/user/delete", "/department/set", "/department/delete");
    }
}
