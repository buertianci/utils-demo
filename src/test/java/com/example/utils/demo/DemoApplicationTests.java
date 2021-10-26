package com.example.utils.demo;

import com.example.utils.demo.disrupMq.service.DisruptorMqService;
import lombok.extern.slf4j.Slf4j;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@Slf4j
@RunWith(SpringRunner.class)
@SpringBootTest(classes = DemoApplication.class)
public class DemoApplicationTests {

    @Autowired
    private DisruptorMqService disruptorMqService;

    @Test
    public void contextLoads() {
    }

    /**
     * 项目内部使用Disruptor做消息队列
     * 步骤：
     * 1、添加pom.xml依赖
     * 2、消息体Model
     * 3、构造EventFactory
     * 4、构造EventHandler
     * 5、构造BeanManager
     * 6、构造MQManager
     * 7、构造Mqservice和实现类
     * 8、构造测试类及方法
     * @throws Exception
     */
    @Test
    public void sayHelloMqTest() throws Exception{
        disruptorMqService.sayHelloMq("消息到了，Hello world!");
        log.info("消息队列已发送完毕");
        //这里停止2000ms是为了确定是处理消息是异步的
        Thread.sleep(2000);
    }
}
