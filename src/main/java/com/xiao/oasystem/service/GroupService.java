package com.xiao.oasystem.service;

import com.xiao.oasystem.pojo.DTO.GroupDTO;
import com.xiao.oasystem.pojo.VO.GitProgressVO;
import com.xiao.oasystem.pojo.VO.GroupContentVO;
import com.xiao.oasystem.pojo.VO.Register.GroupRegisterVo;
import com.xiao.oasystem.pojo.entity.Group;
import com.xiao.oasystem.result.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface GroupService {

    public Result<Group> creatGroup(GroupRegisterVo groupRegisterVo);

    public Result<Boolean> deleteGroup(String id);

    public Result<Boolean> setContent(GroupContentVO groupContentVO, String userId);

    public Result<String> getContent(String id);

    public Result<Boolean> setWorkFinished(String id, String userId);

    public Result<Boolean> gitProgress(GitProgressVO gitProgressVO, String userId);

    public Result<Group> getGroup(String id);

    public Result<List<GroupDTO>> getGroupsInDepartment(String department, int pageNum);

    public Result<List<GroupDTO>> list(int pageNum);
}
