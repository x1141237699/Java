package com.xiao.oasystem.controller;

import cn.hutool.core.bean.BeanUtil;
import com.xiao.oasystem.pojo.DTO.UserDTO;
import com.xiao.oasystem.pojo.VO.LoginVO;
import com.xiao.oasystem.pojo.VO.PasswordChangeVO;
import com.xiao.oasystem.pojo.VO.Register.UserRegisterVo;
import com.xiao.oasystem.pojo.VO.UserVO;
import com.xiao.oasystem.pojo.entity.User;
import com.xiao.oasystem.result.Result;
import com.xiao.oasystem.service.UserService;
import com.xiao.oasystem.utils.JWTUtil;
import io.jsonwebtoken.Claims;
import jakarta.servlet.ServletOutputStream;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.File;
import java.io.FileInputStream;
import java.util.List;

@Slf4j
@RestController
@RequestMapping("/user")
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping("/login")
    public Result<String> login(@RequestBody LoginVO loginVO){
        return userService.login(loginVO);
    }

    @PostMapping("/set")
    public Result<User> setUser(@RequestBody UserRegisterVo userRegisterVo){
        return userService.setUser(userRegisterVo);
    }

    @PostMapping("/change/password")
    public Result<Boolean> changePassword(@RequestBody PasswordChangeVO passwordChangeVO, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return userService.changePassword(passwordChangeVO, userVO.getId());
    }

    @DeleteMapping("/delete")
    public Result<Boolean> deleteUser(@RequestParam String id){
        return userService.deleteUser(id);
    }

    @GetMapping("/id")
    public Result<User> getUser(@RequestParam String id){
        return userService.getUser(id);
    }

    @GetMapping("/get/my/information")
    public Result<User> getMyInformation(HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return userService.getUser(userVO.getId());
    }

    @GetMapping("/department")
    public Result<List<UserDTO>> getUsersInDepartment(@RequestParam String department, @RequestParam(defaultValue = "1") Integer pageNum){
        return userService.getUsersInDepartment(department, pageNum);
    }

    @GetMapping("/group")
    public Result<List<UserDTO>> getUsersInGroup(@RequestParam String group, @RequestParam(defaultValue = "1") Integer pageNum){
        return userService.getUsersInGroup(group, pageNum);
    }

    @PostMapping("/uploadImg")
    public Result<Boolean> uploadImg(@RequestParam MultipartFile multipartFile, HttpServletRequest request){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        return userService.uploadImg(multipartFile, userVO.getId());
    }

    @GetMapping("/get/my/img")
    public void getImg(HttpServletRequest request, HttpServletResponse response){
        String token = request.getHeader("token");
        Claims claims = JWTUtil.parseJWT(token);
        UserVO userVO = BeanUtil.toBean(claims, UserVO.class);
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Kevin\\Desktop\\Java\\img\\" + userVO.getName());

            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @GetMapping("/get/img/by/id")
    public void getImg(@RequestParam("id") String id, HttpServletResponse response){
        User user = userService.getUser(id).getData();
        try {
            //输入流，通过输入流读取文件内容
            FileInputStream fileInputStream = new FileInputStream("C:\\Users\\Kevin\\Desktop\\Java\\img\\" + user.getName());

            //输出流，通过输出流将文件写回浏览器
            ServletOutputStream outputStream = response.getOutputStream();

            response.setContentType("image/jpeg");

            int len;
            byte[] bytes = new byte[1024];
            while ((len = fileInputStream.read(bytes)) != -1){
                outputStream.write(bytes,0,len);
                outputStream.flush();
            }

            //关闭资源
            outputStream.close();
            fileInputStream.close();
        } catch (Exception e) {
            log.error(e.getMessage());
        }
    }

    @GetMapping("/list")
    public Result<List<UserDTO>> list(@RequestParam(defaultValue = "1") Integer pageNum){
        return userService.list(pageNum);
    }
}
