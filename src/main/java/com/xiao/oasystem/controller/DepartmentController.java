package com.xiao.oasystem.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xiao.oasystem.pojo.DTO.DepartmentDTO;
import com.xiao.oasystem.pojo.VO.DepartmentAnnouncementVO;
import com.xiao.oasystem.pojo.VO.UserVO;
import com.xiao.oasystem.pojo.entity.Department;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.DepartmentService;
import com.xiao.oasystem.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/department")
public class DepartmentController {

    @Autowired
    private DepartmentService departmentService;

    @PostMapping("/set")
    public Result<Department> creatDepartment(@RequestParam String name){
        return departmentService.creatDepartment(name);
    }

    @PostMapping("/set/announcement")
    public Result<Boolean> setAnnouncement(@RequestBody DepartmentAnnouncementVO departmentAnnouncementVO, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return departmentService.setAnnouncement(departmentAnnouncementVO, userVO.getId());
    }

    @GetMapping("/get/announcement")
    public Result<String> getAnnouncement(@RequestParam String id){
        return departmentService.getAnnouncement(id);
    }

    @GetMapping("/get/work/content")
    public Result<Map<String, String>> getWorkContent(@RequestParam String id, @RequestParam(defaultValue = "1") Integer pageNum){
        return departmentService.getWorkContent(id, pageNum);
    }

    @DeleteMapping("/delete")
    public Result<Boolean> deleteDepartment(@RequestParam String id){
        return departmentService.deleteDepartment(id);
    }

    @GetMapping("/id")
    public Result<Department> getDepartment(@RequestParam String id){
        return departmentService.getDepartment(id);
    }

    @GetMapping("/list")
    public Result<List<DepartmentDTO>> list(@RequestParam(defaultValue = "1") Integer pageNum){
        return departmentService.list(pageNum);
    }
}
