package com.xiao.oasystem.mapper;

import com.xiao.oasystem.pojo.entity.ApplicationForDepartment;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplicationForDepartmentMapper {

    public void insertApplication(ApplicationForDepartment application);

    public void deleteApplication(@Param("id")String id);

    public ApplicationForDepartment getApplicationById(@Param("id")String id);

    public List<ApplicationForDepartment> getApplicationsByInitiator(@Param("initiator")String initiator);

    public List<ApplicationForDepartment> getApplicationsByOutDepartment(@Param("outDepartment")String outDepartment);

    public List<ApplicationForDepartment> getApplicationsByInDepartment(@Param("inDepartment")String inDepartment);

    public void updateApplicationState(@Param("id")String id,@Param("state")int state);

    public List<ApplicationForDepartment> list();
}
