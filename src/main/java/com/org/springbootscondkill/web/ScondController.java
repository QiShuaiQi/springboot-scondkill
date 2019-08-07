package com.org.springbootscondkill.web;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.client.RestTemplate;
import java.util.concurrent.CountDownLatch;

@Controller

public class ScondController extends Thread {
    private Logger logger= LoggerFactory.getLogger(ScondController.class);
    private static final int nut = 5000;//可以调换线程的并发数
    private static CountDownLatch count = new CountDownLatch(nut);
    RestTemplate restTemplate=new RestTemplate();
    private  String url="http://127.0.0.1:8080/end";
    @Autowired
    private StringRedisTemplate redisTemplate;

    @RequestMapping("/")
    public Object front() throws Exception {

        return "index";
    }
    @RequestMapping("test")
    public  Object    test()throws Exception{

        for (int a=0;a<nut;a++){
            new Thread(new UserRequst()).start();
            count.countDown();
        }
        Thread.sleep(4000);

        return  "redirect:/";
    }



    @RequestMapping( value = "end" ,method = RequestMethod.GET)
    public Object end()throws  Exception {

        redisTemplate.opsForValue().set("1", "成功");

        String result = redisTemplate.opsForValue().get("1");
        if (result != null) {
            redisTemplate.opsForValue().set("success", "成功秒杀");

            String redisstring = redisTemplate.opsForValue().get("success");
            logger.info(redisstring);
        }
        return "redirect:/";
    }

    public  class  UserRequst  implements  Runnable {

        @Override
        public void run() {
            try {
                count.await();
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

                restTemplate.getForEntity(url, String.class).getBody();


        }
    }
}


