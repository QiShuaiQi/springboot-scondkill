package com.org.springbootscondkill;

import org.junit.Test;
import org.junit.runner.RunWith;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.test.context.junit4.SpringRunner;

import javax.annotation.Resource;

@RunWith(SpringRunner.class)
@SpringBootTest

public class SpringbootScondkillApplicationTests {
   @Autowired
    StringRedisTemplate redisTemplate;
@Test
    public  void  test1() {
 String reids=    redisTemplate.opsForValue().get("kill");
    System.out.println(reids);
}


}
