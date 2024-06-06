package com.xiao.oasystem.service;

import com.xiao.oasystem.pojo.DTO.ApplicationForDepartmentDTO;
import com.xiao.oasystem.pojo.entity.ApplicationForDepartment;
import com.xiao.oasystem.result.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ApplicationForDepartmentService {

    public Result<ApplicationForDepartment> creatApplication(String inDepartment, String userId);

    public Result<Boolean> submitApplication(String id, String userId);

    public Result<Boolean> deleteApplication(String id, String userId);

    public Result<List<ApplicationForDepartmentDTO>> getApplicationsForInitiator(String initiator, int pageNum);

    public Result<List<ApplicationForDepartmentDTO>> getApplicationsForOutDepartment(String outDepartment, int pageNum);

    public Result<List<ApplicationForDepartmentDTO>> getApplicationsForInDepartment(String inDepartment, int pageNum);

    public Result<ApplicationForDepartment> getApplication(String id);

    public Result<Boolean> agreeApplication(String id, String userId);

    public Result<Boolean> refuseApplication(String id, String userId);

    public Result<List<ApplicationForDepartmentDTO>> list(int pageNum);
}
