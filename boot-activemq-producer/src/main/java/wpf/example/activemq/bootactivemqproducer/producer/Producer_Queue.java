package wpf.example.activemq.bootactivemqproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Queue;

@Component
public class Producer_Queue {

    @Autowired
    private JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    private Queue queue;

    public void produceMsg(){
        jmsMessagingTemplate.convertAndSend(queue,"msg in boot");
    }

    //三秒定时发送消息，间隔投放；需要主启动类开启；然后直接启动主启动类定时发送消息
    @Scheduled(fixedDelay = 3000L)
    public void prduceceMsgScheduled(){
        System.out.println("send ok....");
        jmsMessagingTemplate.convertAndSend(queue,"msg in boot");
    }
}
