package com.xiao.oasystem.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xiao.oasystem.pojo.DTO.GroupDTO;
import com.xiao.oasystem.pojo.VO.GitProgressVO;
import com.xiao.oasystem.pojo.VO.GroupContentVO;
import com.xiao.oasystem.pojo.VO.Register.GroupRegisterVo;
import com.xiao.oasystem.pojo.VO.UserVO;
import com.xiao.oasystem.pojo.entity.Group;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.GroupService;
import com.xiao.oasystem.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.http.HttpServletRequest;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/group")
public class GroupController {

    @Autowired
    private GroupService groupService;

    @PostMapping("/set")
    public Result<Group> creatGroup(@RequestBody GroupRegisterVo groupRegisterVo){
        return groupService.creatGroup(groupRegisterVo);
    }

    @DeleteMapping("/delete")
    public Result<Boolean> deleteGroup(@RequestParam String id){
        return groupService.deleteGroup(id);
    }

    @PostMapping("/set/content")
    public Result<Boolean> setContent(@RequestBody GroupContentVO groupContentVO, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return groupService.setContent(groupContentVO, userVO.getId());
    }

    @GetMapping("/get/content")
    public Result<String> getContent(@RequestParam String id){
        return groupService.getContent(id);
    }

    @PostMapping("/set/work/finished")
    public Result<Boolean> setWorkFinished(@RequestParam String id, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return groupService.setWorkFinished(id, userVO.getId());
    }

    @PostMapping("/git/progress")
    public Result<Boolean> setWorkFinished(@RequestBody GitProgressVO gitProgressVO, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return groupService.gitProgress(gitProgressVO, userVO.getId());
    }

    @GetMapping("/id")
    public Result<Group> getGroup(@RequestParam String id){
        return groupService.getGroup(id);
    }

    @GetMapping("/department")
    public Result<List<GroupDTO>> getGroupsInDepartment(@RequestParam String department, @RequestParam(defaultValue = "1") Integer pageNum){
        return groupService.getGroupsInDepartment(department, pageNum);
    }

    @GetMapping("/list")
    public Result<List<GroupDTO>> list(@RequestParam(defaultValue = "1") Integer pageNum){
        return groupService.list(pageNum);
    }
}
