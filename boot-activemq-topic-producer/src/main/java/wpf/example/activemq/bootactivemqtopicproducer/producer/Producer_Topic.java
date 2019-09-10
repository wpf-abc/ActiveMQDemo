package wpf.example.activemq.bootactivemqtopicproducer.producer;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jms.core.JmsMessagingTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
public class Producer_Topic {
    @Autowired
    JmsMessagingTemplate jmsMessagingTemplate;

    @Autowired
    Topic topic;

    @Scheduled(fixedDelay = 3000)
    public void produce(){
        jmsMessagingTemplate.convertAndSend(topic,"msg in boot by topic");
    }

}
