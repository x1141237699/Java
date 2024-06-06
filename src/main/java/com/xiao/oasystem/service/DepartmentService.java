package com.xiao.oasystem.service;

import com.xiao.oasystem.pojo.DTO.DepartmentDTO;
import com.xiao.oasystem.pojo.VO.DepartmentAnnouncementVO;
import com.xiao.oasystem.pojo.entity.Department;
import com.xiao.oasystem.result.Result;

import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Map;

@Transactional
public interface DepartmentService {

    public Result<Department> creatDepartment(String name);

    public Result<Boolean> setAnnouncement(DepartmentAnnouncementVO departmentAnnouncementVO, String userId);

    public Result<String> getAnnouncement(String id);

    public Result<Map<String, String>> getWorkContent(String id, int pageNum);

    public Result<Boolean> deleteDepartment(String id);

    public Result<Department> getDepartment(String id);

    public Result<List<DepartmentDTO>> list(int pageNum);
}
