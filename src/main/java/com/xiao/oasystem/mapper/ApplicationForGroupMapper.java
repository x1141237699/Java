package com.xiao.oasystem.mapper;

import com.xiao.oasystem.pojo.entity.ApplicationForGroup;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplicationForGroupMapper {

    public void insertApplication(ApplicationForGroup application);

    public void deleteApplication(@Param("id")String id);

    public ApplicationForGroup getApplicationById(@Param("id")String id);

    public List<ApplicationForGroup> getApplicationsByInitiator(@Param("initiator")String initiator);

    public List<ApplicationForGroup> getApplicationsByOutGroup(@Param("outGroup")String outGroup);

    public List<ApplicationForGroup> getApplicationsByInGroup(@Param("inGroup")String inGroup);

    public void updateApplicationState(@Param("id")String id,@Param("state")int state);

    public List<ApplicationForGroup> list();

}
