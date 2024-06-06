package com.xiao.oasystem.service.impl;

import com.xiao.oasystem.exception.NoSuchUserException;
import com.xiao.oasystem.pojo.DTO.ApplicationDTO;
import com.xiao.oasystem.pojo.entity.User;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.*;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

@Service
public class ApplicationServiceImpl implements ApplicationService {

    @Autowired
    private UserService userService;

    @Autowired
    private ApplicationForDepartmentService applicationForDepartmentService;

    @Autowired
    private ApplicationForGroupService applicationForGroupService;

    @Autowired
    private ApplicationForHolidayService applicationForHolidayService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public Result<List<ApplicationDTO>> getApplicationPending(String userId, int pageNum) {
        User user = userService.getUser(userId).getData();
        if(null == user)
            throw new NoSuchUserException("不存在此用户");
        Set<String> toBeSubmit = redisTemplate.opsForSet().members("TOBESUBMIT:" + userId);
        Set<String> group = new HashSet<>();
        Set<String> department = new HashSet<>();
        Set<String> holiday = new HashSet<>();
        switch (user.getPosition()){
            case User.GROUP_MANAGER -> {
                group = redisTemplate.opsForSet().members("GROUP:" + user.getGroup());
                holiday = redisTemplate.opsForSet().members("HOLIDAY:GROUP:" + user.getGroup());
            }
            case User.DEPARTMENT_MANAGER -> {
                department = redisTemplate.opsForSet().members("DEPARTMENT:" + user.getDepartment());
                holiday = redisTemplate.opsForSet().members("HOLIDAY:DEPARTMENT:" + user.getDepartment());
            }
        }

        List<ApplicationDTO> list = new ArrayList<>();
        if (toBeSubmit != null) {
            for (String s : toBeSubmit) {
                if(null != applicationForDepartmentService.getApplication(s).getData())
                    list.add(new ApplicationDTO(applicationForDepartmentService.getApplication(s).getData()));
                if(null != applicationForGroupService.getApplication(s).getData())
                    list.add(new ApplicationDTO(applicationForGroupService.getApplication(s).getData()));
                if(null != applicationForHolidayService.getApplication(s).getData())
                    list.add(new ApplicationDTO(applicationForHolidayService.getApplication(s).getData()));
            }
        }
        if (group != null) {
            for (String s : group) {
                list.add(new ApplicationDTO(applicationForGroupService.getApplication(s).getData()));
            }
        }
        if (department != null) {
            for (String s : department) {
                list.add(new ApplicationDTO(applicationForDepartmentService.getApplication(s).getData()));
            }
        }
        if (holiday != null) {
            for (String s : holiday) {
                list.add(new ApplicationDTO(applicationForHolidayService.getApplication(s).getData()));
            }
        }

        List<ApplicationDTO> result = new ArrayList<>();
        list.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(result::add);
        return Result.success(Result.SUCCESS, "操作成功", result);
    }
}
