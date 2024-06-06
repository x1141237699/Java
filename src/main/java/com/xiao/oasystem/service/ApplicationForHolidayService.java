package com.xiao.oasystem.service;

import com.xiao.oasystem.pojo.DTO.ApplicationForHolidayDTO;
import com.xiao.oasystem.pojo.entity.ApplicationForHoliday;
import com.xiao.oasystem.result.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ApplicationForHolidayService {

    public Result<ApplicationForHoliday> creatApplication(String reason, String userId);

    public Result<Boolean> submitApplication(String id, String userId);

    public Result<Boolean> deleteApplication(String id, String userId);

    public Result<List<ApplicationForHolidayDTO>> getApplicationsForInitiator(String initiator, int pageNum);

    public Result<List<ApplicationForHolidayDTO>> getApplicationsForRecipient(String recipient, int pageNum);

    public Result<ApplicationForHoliday> getApplication(String id);

    public Result<Boolean> agreeApplication(String id, String userId);

    public Result<Boolean> refuseApplication(String id, String userId);

    public Result<Boolean> rejectApplication(String id, String userId);

    public Result<List<ApplicationForHolidayDTO>> list(int pageNum);
}
