package com.xiao.oasystem.service;

import com.xiao.oasystem.pojo.DTO.ApplicationForGroupDTO;
import com.xiao.oasystem.pojo.entity.ApplicationForGroup;
import com.xiao.oasystem.result.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ApplicationForGroupService {

    public Result<ApplicationForGroup> creatApplication(String inGroup, String userId);

    public Result<Boolean> submitApplication(String id, String userId);

    public Result<Boolean> deleteApplication(String id, String userId);

    public Result<List<ApplicationForGroupDTO>> getApplicationsForInitiator(String initiator, int pageNum);

    public Result<List<ApplicationForGroupDTO>> getApplicationsForOutGroup(String outGroup, int pageNum);

    public Result<List<ApplicationForGroupDTO>> getApplicationsForInGroup(String inGroup, int pageNum);

    public Result<ApplicationForGroup> getApplication(String id);

    public Result<Boolean> agreeApplication(String id, String userId);

    public Result<Boolean> refuseApplication(String id, String userId);

    public Result<List<ApplicationForGroupDTO>> list(int pageNum);
}
