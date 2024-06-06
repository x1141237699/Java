package com.xiao.oasystem;

import cn.hutool.core.bean.BeanUtil;
import com.rabbitmq.client.Channel;
import com.xiao.oasystem.config.rabbitConfig.RecordConfig;
import com.xiao.oasystem.mapper.UserMapper;
import com.xiao.oasystem.pojo.VO.Register.UserRegisterVo;
import com.xiao.oasystem.pojo.VO.UserVO;
import com.xiao.oasystem.pojo.entity.User;
import com.xiao.oasystem.service.UserService;
import jakarta.annotation.Resource;
import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.StringRedisTemplate;

import java.io.File;
import java.util.ArrayList;
import java.util.Date;
import java.util.HashMap;
import java.util.List;

@SpringBootTest
class OAsystemApplicationTests {

    @Test
    void contextLoads() {
        File file = new File("C:\\Users\\Kevin\\Desktop\\Java\\img\\" + new Date().getTime());
        System.out.println(file);
    }

}
