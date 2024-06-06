package com.xiao.oasystem.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xiao.oasystem.pojo.DTO.ApplicationForHolidayDTO;
import com.xiao.oasystem.pojo.VO.UserVO;
import com.xiao.oasystem.pojo.entity.ApplicationForHoliday;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.ApplicationForHolidayService;
import com.xiao.oasystem.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/application/for/holiday")
public class ApplicationForHolidayController {

    @Autowired
    private ApplicationForHolidayService applicationForHolidayService;

    @PostMapping("/set")
    public Result<ApplicationForHoliday> creatApplication(@RequestParam String reason, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForHolidayService.creatApplication(reason, userVO.getId());
    }

    @PostMapping("/submit")
    public Result<Boolean> submitApplication(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForHolidayService.submitApplication(id, userVO.getId());
    }

    @DeleteMapping("/delete")
    public Result<Boolean> deleteApplication(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForHolidayService.deleteApplication(id, userVO.getId());
    }

    @GetMapping("/get/by/initiator")
    public Result<List<ApplicationForHolidayDTO>> getApplicationsForInitiator(@RequestParam String initiator, @RequestParam(defaultValue = "1") Integer pageNum){
        return applicationForHolidayService.getApplicationsForInitiator(initiator, pageNum);
    }

    @GetMapping("/my/application")
    public Result<List<ApplicationForHolidayDTO>> getMyApplications(HttpServletRequest request, @RequestParam(defaultValue = "1") Integer pageNum){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForHolidayService.getApplicationsForInitiator(userVO.getId(), pageNum);
    }

    @GetMapping("/get/by/recipient")
    public Result<List<ApplicationForHolidayDTO>> getApplicationsForRecipient(@RequestParam String recipient, @RequestParam(defaultValue = "1") Integer pageNum){
        return applicationForHolidayService.getApplicationsForRecipient(recipient, pageNum);
    }

    @GetMapping("/id")
    public Result<ApplicationForHoliday> getApplication(@RequestParam String id){
        return applicationForHolidayService.getApplication(id);
    }

    @PostMapping("/agree")
    public Result<Boolean> agreeApplication(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForHolidayService.agreeApplication(id, userVO.getId());
    }

    @PostMapping("/refuse")
    public Result<Boolean> refuseApplication(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForHolidayService.refuseApplication(id, userVO.getId());
    }

    @PostMapping("/reject")
    public Result<Boolean> rejectApplication(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return applicationForHolidayService.rejectApplication(id, userVO.getId());
    }

    @GetMapping("/list")
    private Result<List<ApplicationForHolidayDTO>> list(@RequestParam(defaultValue = "1") int pageNum){
        return applicationForHolidayService.list(pageNum);
    }
}
