package com.xiao.oasystem.service;

import com.xiao.oasystem.pojo.DTO.ApplicationDTO;
import com.xiao.oasystem.result.Result;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

@Transactional
public interface ApplicationService {

    public Result<List<ApplicationDTO>> getApplicationPending(String userId, int pageNum);

}
