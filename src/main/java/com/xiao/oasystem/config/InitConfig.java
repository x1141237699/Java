package com.xiao.oasystem.config;

import com.xiao.oasystem.pojo.DTO.ApplicationForDepartmentDTO;
import com.xiao.oasystem.pojo.DTO.ApplicationForGroupDTO;
import com.xiao.oasystem.pojo.DTO.ApplicationForHolidayDTO;
import com.xiao.oasystem.pojo.entity.ApplicationForDepartment;
import com.xiao.oasystem.pojo.entity.ApplicationForGroup;
import com.xiao.oasystem.pojo.entity.ApplicationForHoliday;
import com.xiao.oasystem.service.ApplicationForDepartmentService;
import com.xiao.oasystem.service.ApplicationForGroupService;
import com.xiao.oasystem.service.ApplicationForHolidayService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.ApplicationArguments;
import org.springframework.boot.ApplicationRunner;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class InitConfig implements ApplicationRunner {

    @Autowired
    private ApplicationForDepartmentService applicationForDepartmentService;

    @Autowired
    private ApplicationForGroupService applicationForGroupService;

    @Autowired
    private ApplicationForHolidayService applicationForHolidayService;

    @Autowired
    private StringRedisTemplate redisTemplate;

    @Override
    public void run(ApplicationArguments args) {
        List<ApplicationForDepartmentDTO> applicationForDepartmentDTOS = applicationForDepartmentService.list(0).getData();
        for (ApplicationForDepartmentDTO applicationForDepartmentDTO : applicationForDepartmentDTOS) {
            ApplicationForDepartment applicationForDepartment = applicationForDepartmentService.getApplication(applicationForDepartmentDTO.getId()).getData();
            int state = applicationForDepartment.getState();
            switch (state){
                case ApplicationForDepartment.TO_BE_SUBMITTED -> redisTemplate.opsForSet().add("TOBESUBMIT:" + applicationForDepartment.getInitiator(), applicationForDepartment.getId());
                case ApplicationForDepartment.SUBMITTED -> redisTemplate.opsForSet().add("DEPARTMENT:" + applicationForDepartment.getOutDepartment(), applicationForDepartment.getId());
                case ApplicationForDepartment.PASS_FOR_OUT -> redisTemplate.opsForSet().add("DEPARTMENT:" + applicationForDepartment.getInDepartment(), applicationForDepartment.getId());
            }
        }

        List<ApplicationForGroupDTO> applicationForGroupDTOS = applicationForGroupService.list(0).getData();
        for (ApplicationForGroupDTO applicationForGroupDTO : applicationForGroupDTOS) {
            ApplicationForGroup applicationForGroup = applicationForGroupService.getApplication(applicationForGroupDTO.getId()).getData();
            int state = applicationForGroup.getState();
            switch (state){
                case ApplicationForGroup.TO_BE_SUBMITTED -> redisTemplate.opsForSet().add("TOBESUBMIT:" + applicationForGroup.getInitiator(), applicationForGroup.getId());
                case ApplicationForGroup.SUBMITTED -> redisTemplate.opsForSet().add("GROUP:" + applicationForGroup.getOutGroup(), applicationForGroup.getId());
                case ApplicationForGroup.PASS_FOR_OUT -> redisTemplate.opsForSet().add("GROUP:" + applicationForGroup.getInGroup(), applicationForGroup.getId());
            }
        }

        List<ApplicationForHolidayDTO> applicationForHolidayDTOS = applicationForHolidayService.list(0).getData();
        for (ApplicationForHolidayDTO applicationForHolidayDTO : applicationForHolidayDTOS) {
            ApplicationForHoliday applicationForHoliday = applicationForHolidayService.getApplication(applicationForHolidayDTO.getId()).getData();
            int state = applicationForHoliday.getState();
            switch (state){
                case ApplicationForHoliday.TO_BE_SUBMITTED -> redisTemplate.opsForSet().add("TOBESUBMIT:" + applicationForHoliday.getInitiator(), applicationForHoliday.getId());
                case ApplicationForHoliday.SUBMITTED -> {
                    switch (applicationForHoliday.getVariety()){
                        case ApplicationForHoliday.GROUP -> redisTemplate.opsForSet().add("HOLIDAY:GROUP:" + applicationForHoliday.getRecipient(), applicationForHoliday.getId());
                        case ApplicationForHoliday.DEPARTMENT -> redisTemplate.opsForSet().add("HOLIDAY:DEPARTMENT:" + applicationForHoliday.getRecipient(), applicationForHoliday.getId());
                    }
                }
            }
        }
    }
}
