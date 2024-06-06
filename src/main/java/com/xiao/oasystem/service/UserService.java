package com.xiao.oasystem.service;

import com.xiao.oasystem.pojo.DTO.UserDTO;
import com.xiao.oasystem.pojo.VO.LoginVO;
import com.xiao.oasystem.pojo.VO.PasswordChangeVO;
import com.xiao.oasystem.pojo.VO.Register.UserRegisterVo;
import com.xiao.oasystem.pojo.VO.UserDepartmentVO;
import com.xiao.oasystem.pojo.VO.UserGroupVO;
import com.xiao.oasystem.pojo.entity.User;
import com.xiao.oasystem.result.Result;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

@Transactional
public interface UserService {

    public Result<String> login(LoginVO loginVO);

    public Result<User> setUser(UserRegisterVo user);

    public Result<Boolean> changePassword(PasswordChangeVO passwordChangeVO, String userId);

    public Result<Boolean> deleteUser(String id);

    public Result<Boolean> updateUserDepartment(UserDepartmentVO userDepartmentVO);

    public Result<Boolean> updateUserGroup(UserGroupVO userGroupVO);

    public Result<User> getUser(String id);

    public Result<List<UserDTO>> getUsersInDepartment(String department, int pageNum);

    public Result<List<UserDTO>> getUsersInGroup(String group, int pageNum);

    public Result<Boolean> uploadImg(MultipartFile multipartFile, String userId);

    public Result<List<UserDTO>> list(int pageNum);
}
