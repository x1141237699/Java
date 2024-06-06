package com.xiao.oasystem.mapper;

import com.xiao.oasystem.pojo.VO.PasswordChangeVO;
import com.xiao.oasystem.pojo.entity.User;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;

@Mapper
public interface UserMapper {

    public void insertUser(User user);

    public void updatePassword(@Param("password") String password,@Param("id") String id);

    public void deleteUser(@Param("id")String id);

    public void updateUserDepartment(@Param("id")String id, @Param("department")String department);

    public void updateUserGroup(@Param("id")String id, @Param("group")String group);

    public User getUserById(@Param("id")String id);

    public List<User> getUsersByPosition(@Param("position")int position);

    public List<User> getUsersByDepartment(@Param("department")String department);

    public List<User> getUsersByGroup(@Param("group")String group);

    public List<User> list();
}
