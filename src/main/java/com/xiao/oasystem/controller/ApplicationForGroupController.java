package com.xiao.oasystem.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xiao.oasystem.pojo.DTO.ApplicationForGroupDTO;
import com.xiao.oasystem.pojo.VO.UserVO;
import com.xiao.oasystem.pojo.entity.ApplicationForGroup;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.ApplicationForGroupService;
import com.xiao.oasystem.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/application/for/group")
public class ApplicationForGroupController {

    @Autowired
    private ApplicationForGroupService applicationForGroupService;

    @PostMapping("/set")
    public Result<ApplicationForGroup> creatApplication(@RequestParam String inGroup, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForGroupService.creatApplication(inGroup, userVO.getId());
    }

    @PostMapping("/submit")
    public Result<Boolean> submitApplication(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForGroupService.submitApplication(id, userVO.getId());
    }

    @DeleteMapping("/delete")
    public Result<Boolean> deleteApplication(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForGroupService.deleteApplication(id, userVO.getId());
    }

    @GetMapping("/get/for/initiator")
    public Result<List<ApplicationForGroupDTO>> getApplicationsForInitiator(@RequestParam String initiator, @RequestParam(defaultValue = "1") Integer pageNum){
        return applicationForGroupService.getApplicationsForInitiator(initiator, pageNum);
    }

    @GetMapping("/get/my/application")
    public Result<List<ApplicationForGroupDTO>> getMyApplications(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForGroupService.getApplicationsForInitiator(userVO.getId(), pageNum);
    }

    @GetMapping("/get/for/out/group")
    public Result<List<ApplicationForGroupDTO>> getApplicationsForOutGroup(@RequestParam String outGroup, @RequestParam(defaultValue = "1") Integer pageNum){
        return applicationForGroupService.getApplicationsForOutGroup(outGroup, pageNum);
    }

    @GetMapping("/get/for/in/group")
    public Result<List<ApplicationForGroupDTO>> getApplicationsForInGroup(@RequestParam String inGroup, @RequestParam(defaultValue = "1") Integer pageNum){
        return applicationForGroupService.getApplicationsForInGroup(inGroup, pageNum);
    }

    @GetMapping("/id")
    public Result<ApplicationForGroup> getApplication(@RequestParam String id){
        return applicationForGroupService.getApplication(id);
    }

    @PostMapping("/agree")
    public Result<Boolean> agreeApplication(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForGroupService.agreeApplication(id, userVO.getId());
    }

    @PostMapping("/refuse")
    public Result<Boolean> refuseApplication(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForGroupService.refuseApplication(id, userVO.getId());
    }

    @GetMapping("/list")
    public Result<List<ApplicationForGroupDTO>> list(@RequestParam(defaultValue = "1") int pageNum){
        return applicationForGroupService.list(pageNum);
    }
}
