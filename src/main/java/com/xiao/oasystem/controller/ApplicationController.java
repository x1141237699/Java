package com.xiao.oasystem.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xiao.oasystem.pojo.DTO.ApplicationDTO;
import com.xiao.oasystem.pojo.VO.UserVO;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.ApplicationService;
import com.xiao.oasystem.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/application")
public class ApplicationController {

    @Autowired
    private ApplicationService applicationService;

    @GetMapping("/get/application/pending")
    public Result<List<ApplicationDTO>> getApplicationPending(HttpServletRequest request, @RequestParam(defaultValue = "1") int pageNum){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationService.getApplicationPending(userVO.getId(), pageNum);
    }
}
