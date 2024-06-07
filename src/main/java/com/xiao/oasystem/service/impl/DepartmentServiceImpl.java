package com.xiao.oasystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xiao.oasystem.config.rabbitConfig.RecordConfig;
import com.xiao.oasystem.exception.ExceedPagingLimitException;
import com.xiao.oasystem.exception.NoSuchDepartmentException;
import com.xiao.oasystem.mapper.DepartmentMapper;
import com.xiao.oasystem.pojo.DTO.DepartmentDTO;
import com.xiao.oasystem.pojo.DTO.GroupDTO;
import com.xiao.oasystem.pojo.VO.DepartmentAnnouncementVO;
import com.xiao.oasystem.pojo.entity.Department;
import com.xiao.oasystem.pojo.entity.Group;
import com.xiao.oasystem.pojo.entity.Record;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.DepartmentService;
import com.xiao.oasystem.service.GroupService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.*;

@Service
public class DepartmentServiceImpl implements DepartmentService {

    @Autowired
    private DepartmentMapper departmentMapper;

    @Autowired
    private GroupService groupService;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Result<Department> creatDepartment(String name) {
        Department department = new Department(String.valueOf(new Random().nextLong(99999999999L)), name, null);
        departmentMapper.insertDepartment(department);
        return Result.success(Result.SUCCESS, "操作成功", department);
    }

    @Override
    public Result<Boolean> setAnnouncement(DepartmentAnnouncementVO departmentAnnouncementVO, String userId) {
        Department department = departmentMapper.getDepartmentById(departmentAnnouncementVO.getId());
        if(null == department)
            throw new NoSuchDepartmentException(departmentAnnouncementVO.getId());
        departmentMapper.setAnnouncement(departmentAnnouncementVO);
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "发布部门公告:" + department.getId() + ":" + departmentAnnouncementVO.getAnnouncement(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<String> getAnnouncement(String id) {
        Department department = departmentMapper.getDepartmentById(id);
        if(null == department)
            throw new NoSuchDepartmentException(id);
        return Result.success(Result.SUCCESS, "操作成功", department.getAnnouncement());
    }

    @Override
    public Result<Map<String, String>> getWorkContent(String id, int pageNum) {
        Department department = departmentMapper.getDepartmentById(id);
        if(null == department)
            throw new NoSuchDepartmentException(id);
        List<GroupDTO> groupsInDepartment = groupService.getGroupsInDepartment(id, 0).getData();
        List<Group> groups = new ArrayList<>();
        Map<String, String> result = new HashMap<>();
        groupsInDepartment.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(groupDTO -> groups.add(groupService.getGroup(groupDTO.getId()).getData()));
        groups.stream()
                .forEach(group -> result.put(group.getName(),group.getContent()));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }

    @Override
    public Result<Boolean> deleteDepartment(String id) {
        Department department = departmentMapper.getDepartmentById(id);
        if(null == department)
            throw new NoSuchDepartmentException(id);
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Department> getDepartment(String id) {
        Department department = departmentMapper.getDepartmentById(id);
        if(null == department)
            throw new NoSuchDepartmentException(id);
        return Result.success(Result.SUCCESS, "操作成功", department);
    }

    @Override
    public Result<List<DepartmentDTO>> list(int pageNum) {
        List<Department> list = departmentMapper.list();
        List<DepartmentDTO> result = new ArrayList<>();
        list.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(department -> result.add(BeanUtil.toBean(department, DepartmentDTO.class)));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功" ,result);
    }
}
