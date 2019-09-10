package wpf.example.activemq.bootactivemqproducer.config;

import org.apache.activemq.command.ActiveMQQueue;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.jms.annotation.EnableJms;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
@EnableJms  //开启注解驱动
public class MyConfig {
    @Value("${myqueue}")
    private String myQueue;

    @Bean
    public Queue queue(){
        return new ActiveMQQueue(myQueue);
    }
}
