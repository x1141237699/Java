package com.xiao.oasystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xiao.oasystem.config.rabbitConfig.RecordConfig;
import com.xiao.oasystem.exception.ExceedPagingLimitException;
import com.xiao.oasystem.exception.NoSuchGroupException;
import com.xiao.oasystem.mapper.GroupMapper;
import com.xiao.oasystem.pojo.DTO.GroupDTO;
import com.xiao.oasystem.pojo.VO.GitProgressVO;
import com.xiao.oasystem.pojo.VO.GroupContentVO;
import com.xiao.oasystem.pojo.VO.Register.GroupRegisterVo;
import com.xiao.oasystem.pojo.entity.Group;
import com.xiao.oasystem.pojo.entity.Record;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.GroupService;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.SerializationUtils;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Random;

@Service
public class GroupServiceImpl implements GroupService {

    @Autowired
    private GroupMapper groupMapper;

    @Autowired
    private RabbitTemplate rabbitTemplate;

    @Override
    public Result<Group> creatGroup(GroupRegisterVo groupRegisterVo) {
        Group group = BeanUtil.toBean(groupRegisterVo, Group.class);
        group.setId(String.valueOf(new Random().nextLong(99999999999L)));
        group.setProgress(0);
        group.setState(Group.UNDERWAY);
        groupMapper.insertGroup(group);
        return Result.success(Result.SUCCESS, "操作成功", group);
    }

    @Override
    public Result<Boolean> deleteGroup(String id) {
        Group group = groupMapper.getGroupById(id);
        if(null == group)
            throw new NoSuchGroupException(id);
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Boolean> setContent(GroupContentVO groupContentVO, String userId) {
        Group group = groupMapper.getGroupById(groupContentVO.getId());
        if(null == group)
            throw new NoSuchGroupException(groupContentVO.getId());
        groupMapper.setContent(groupContentVO);
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "发布工作内容:" + group.getId() + ":" + groupContentVO.getContent(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<String> getContent(String id) {
        Group group = groupMapper.getGroupById(id);
        if(null == group)
            throw new NoSuchGroupException(id);
        return Result.success(Result.SUCCESS, "操作成功", group.getContent());
    }

    @Override
    public Result<Boolean> setWorkFinished(String id, String userId) {
        Group group = groupMapper.getGroupById(id);
        if(null == group)
            throw new NoSuchGroupException(id);
        groupMapper.setWorkFinished(id);
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "结束工作:" + group.getId(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Boolean> gitProgress(GitProgressVO gitProgressVO, String userId) {
        Group group = groupMapper.getGroupById(gitProgressVO.getId());
        if(null == group)
            throw new NoSuchGroupException(gitProgressVO.getId());
        if(Group.FINISHED == group.getState())
            return Result.fail(Result.SERVER_FAIL, "工作已结束，无法提交", Boolean.FALSE);
        groupMapper.gitProgress(gitProgressVO);
        rabbitTemplate.convertAndSend(RecordConfig.RECORD_EXCHANGE,
                RecordConfig.RECORD_ROUTING,
                new Message(SerializationUtils.serialize(new Record(userId, "提交工作进度:" + group.getId() + ":" + gitProgressVO.getProgress(), new Date()))));
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Group> getGroup(String id) {
        Group group = groupMapper.getGroupById(id);
        if(null == group)
            throw new NoSuchGroupException(id);
        return Result.success(Result.SUCCESS, "操作成功", group);
    }

    @Override
    public Result<List<GroupDTO>> getGroupsInDepartment(String department, int pageNum) {
        List<GroupDTO> result = new ArrayList<>();
        List<Group> groups = groupMapper.getGroupsByDepartment(department);
        if(0 == pageNum)
            groups.stream()
                    .forEach(group -> result.add(BeanUtil.toBean(group, GroupDTO.class)));
        else{
            groups.stream()
                    .skip(5 * (pageNum - 1))
                    .limit(5)
                    .forEach(group -> result.add(BeanUtil.toBean(group, GroupDTO.class)));
            if(result.isEmpty())
                throw new ExceedPagingLimitException("超出分页上限");
        }
        return Result.success(Result.SUCCESS, "操作成功", result);
    }

    @Override
    public Result<List<GroupDTO>> list(int pageNum) {
        List<GroupDTO> result = new ArrayList<>();
        List<Group> groups = groupMapper.list();
        groups.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(group -> result.add(BeanUtil.toBean(group, GroupDTO.class)));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }
}
