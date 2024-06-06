package com.xiao.oasystem.mapper;

import com.xiao.oasystem.pojo.VO.GitProgressVO;
import com.xiao.oasystem.pojo.VO.GroupContentVO;
import com.xiao.oasystem.pojo.entity.Group;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface GroupMapper {

    public Group getGroupById(@Param("id")String id);

    public void insertGroup(Group group);

    public void deleteGroup(@Param("id")String id);

    public void setContent(GroupContentVO groupContentVO);

    public void setWorkFinished(@Param("id")String id);

    public void gitProgress(GitProgressVO gitProgressVO);

    public List<Group> getGroupsByDepartment(@Param("department")String department);

    public List<Group> list();
}
