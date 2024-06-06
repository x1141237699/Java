package com.xiao.oasystem.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xiao.oasystem.pojo.DTO.ApplicationForDepartmentDTO;
import com.xiao.oasystem.pojo.VO.UserVO;
import com.xiao.oasystem.pojo.entity.ApplicationForDepartment;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.ApplicationForDepartmentService;
import com.xiao.oasystem.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/application/for/department")
public class ApplicationForDepartmentController {

    @Autowired
    private ApplicationForDepartmentService applicationForDepartmentService;

    @PostMapping("/set")
    public Result<ApplicationForDepartment> creatApplication(@RequestParam String inDepartment, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForDepartmentService.creatApplication(inDepartment, userVO.getId());
    }

    @PostMapping("/submit")
    public Result<Boolean> submitApplication(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForDepartmentService.submitApplication(id, userVO.getId());
    }

    @DeleteMapping("/delete")
    public Result<Boolean> deleteApplication(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForDepartmentService.deleteApplication(id, userVO.getId());
    }

    @GetMapping("/get/for/initiator")
    public Result<List<ApplicationForDepartmentDTO>> getApplicationsForInitiator(@RequestParam String initiator, @RequestParam(defaultValue = "1") Integer pageNum){
        return applicationForDepartmentService.getApplicationsForInitiator(initiator, pageNum);
    }

    @GetMapping("/get/my/application")
    public Result<List<ApplicationForDepartmentDTO>> getMyApplication(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForDepartmentService.getApplicationsForInitiator(userVO.getId(), pageNum);
    }

    @GetMapping("/get/for/out/department")
    public Result<List<ApplicationForDepartmentDTO>> getApplicationsForOutDepartment(@RequestParam String outDepartment, @RequestParam(defaultValue = "1") Integer pageNum){
        return applicationForDepartmentService.getApplicationsForOutDepartment(outDepartment, pageNum);
    }

    @GetMapping("/get/fo/in/department")
    public Result<List<ApplicationForDepartmentDTO>> getApplicationsForInDepartment(@RequestParam String inDepartment, @RequestParam(defaultValue = "1") Integer pageNum){
        return applicationForDepartmentService.getApplicationsForInDepartment(inDepartment, pageNum);
    }

    @GetMapping("/id")
    public Result<ApplicationForDepartment> getApplication(@RequestParam String id){
        return applicationForDepartmentService.getApplication(id);
    }

    @PostMapping("/agree")
    public Result<Boolean> agreeApplication(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForDepartmentService.agreeApplication(id, userVO.getId());
    }

    @PostMapping("/refuse")
    public Result<Boolean> refuseApplication(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForDepartmentService.refuseApplication(id, userVO.getId());
    }

    @GetMapping("/list")
    public Result<List<ApplicationForDepartmentDTO>> list(@RequestParam(defaultValue = "1") int pageNum){
        return applicationForDepartmentService.list(pageNum);
    }
}
