package com.xiao.oasystem.service.impl;

import cn.hutool.core.bean.BeanUtil;
import com.xiao.oasystem.exception.ExceedPagingLimitException;
import com.xiao.oasystem.exception.NoSuchUserException;
import com.xiao.oasystem.mapper.UserMapper;
import com.xiao.oasystem.pojo.DTO.UserDTO;
import com.xiao.oasystem.pojo.VO.LoginVO;
import com.xiao.oasystem.pojo.VO.PasswordChangeVO;
import com.xiao.oasystem.pojo.VO.Register.UserRegisterVo;
import com.xiao.oasystem.pojo.VO.UserDepartmentVO;
import com.xiao.oasystem.pojo.VO.UserGroupVO;
import com.xiao.oasystem.pojo.entity.User;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.UserService;
import com.xiao.oasystem.utils.EncryptUtil;
import com.xiao.oasystem.utils.JWTUtil;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Random;

@Service
public class UserServiceImpl implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public Result<String> login(LoginVO loginVO) {
        User user = userMapper.getUserById(loginVO.getId());
        if(EncryptUtil.isOk(loginVO.getPassword(), user.getPassword())){
            String token = JWTUtil.creatToken(user);
            return Result.success(Result.SUCCESS, "登录成功", token);
        }
        return Result.fail(Result.SERVER_FAIL, "登录失败", "FAIL");
    }

    @Override
    public Result<User> setUser(UserRegisterVo userRegisterVo) {
        String confirmPassword = EncryptUtil.encrypt(userRegisterVo.getPassword());
        userRegisterVo.setPassword(confirmPassword);
        User user = BeanUtil.toBean(userRegisterVo, User.class);
        user.setId(String.valueOf(new Random().nextLong(99999999999L)));
        userMapper.insertUser(user);
        return Result.success(Result.SUCCESS, "操作成功", user);
    }

    @Override
    public Result<Boolean> changePassword(PasswordChangeVO passwordChangeVO, String userId) {
        User user = userMapper.getUserById(userId);
        if(null == user)
            throw new NoSuchUserException(userId);
        if(!EncryptUtil.isOk(passwordChangeVO.getOldPassword(), user.getPassword()))
            return Result.fail(Result.SERVER_FAIL, "密码不正确", Boolean.FALSE);
        String password = EncryptUtil.encrypt(passwordChangeVO.getNewPassword());
        userMapper.updatePassword(password, userId);
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Boolean> deleteUser(String id) {
        User user = userMapper.getUserById(id);
        if(null == user)
            throw new NoSuchUserException(id);
        userMapper.deleteUser(id);
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Boolean> updateUserDepartment(UserDepartmentVO userDepartmentVO) {
        User user = userMapper.getUserById(userDepartmentVO.getId());
        if(null == user)
            throw new NoSuchUserException(userDepartmentVO.getId());
        userMapper.updateUserDepartment(userDepartmentVO.getId(), userDepartmentVO.getDepartment());
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<Boolean> updateUserGroup(UserGroupVO userGroupVO) {
        User user = userMapper.getUserById(userGroupVO.getId());
        if(null == user)
            throw new NoSuchUserException(userGroupVO.getId());
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<User> getUser(String id) {
        User user = userMapper.getUserById(id);
        if(null == user)
            throw new NoSuchUserException(id);
        return Result.success(Result.SUCCESS, "操作成功", user);
    }

    @Override
    public Result<List<UserDTO>> getUsersInDepartment(String department, int pageNum) {
        List<UserDTO> result = new ArrayList<>();
        List<User> users = userMapper.getUsersByDepartment(department);
        users.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(user -> result.add(BeanUtil.toBean(user, UserDTO.class)));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }

    @Override
    public Result<List<UserDTO>> getUsersInGroup(String group, int pageNum) {
        List<UserDTO> result = new ArrayList<>();
        List<User> users = userMapper.getUsersByGroup(group);
        users.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(user -> result.add(BeanUtil.toBean(user, UserDTO.class)));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }

    @Override
    public Result<Boolean> uploadImg(MultipartFile multipartFile, String userId) {
        User user = userMapper.getUserById(userId);
        File file = new File("C:\\Users\\Kevin\\Desktop\\Java\\img\\" + user.getName());
        if(!file.exists())
            file.mkdirs();
        try {
            multipartFile.transferTo(file);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
        return Result.success(Result.SUCCESS, "操作成功", Boolean.TRUE);
    }

    @Override
    public Result<List<UserDTO>> list(int pageNum) {
        List<UserDTO> result = new ArrayList<>();
        List<User> users = userMapper.list();
        users.stream()
                .skip(5 * (pageNum - 1))
                .limit(5)
                .forEach(user -> result.add(BeanUtil.toBean(user, UserDTO.class)));
        if(result.isEmpty())
            throw new ExceedPagingLimitException("超出分页上限");
        return Result.success(Result.SUCCESS, "操作成功", result);
    }
}
