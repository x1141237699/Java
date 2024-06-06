package com.xiao.oasystem.mapper;

import com.xiao.oasystem.pojo.VO.DepartmentAnnouncementVO;
import com.xiao.oasystem.pojo.entity.Department;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface DepartmentMapper {

    public void insertDepartment(Department department);

    public void deleteDepartment(@Param("id")String id);

    public void setAnnouncement(DepartmentAnnouncementVO departmentAnnouncementVO);

    public Department getDepartmentById(@Param("id")String id);

    public List<Department> list();
}
