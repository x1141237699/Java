package com.xiao.oasystem.mapper;

import com.xiao.oasystem.pojo.entity.ApplicationForHoliday;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface ApplicationForHolidayMapper {

    public void insertApplication(ApplicationForHoliday application);

    public void deleteApplication(@Param("id")String id);

    public ApplicationForHoliday getApplicationById(@Param("id")String id);

    public List<ApplicationForHoliday> getApplicationsByInitiator(@Param("initiator")String initiator);

    public List<ApplicationForHoliday> getApplicationsByRecipient(@Param("recipient")String recipient);

    public void updateApplicationState(@Param("id")String id,@Param("state")int state);

    public List<ApplicationForHoliday> list();

}
