package wpf.example.activemq.bootactivemqtopicproducer.config;

import org.apache.activemq.command.ActiveMQTopic;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.stereotype.Component;

import javax.jms.Topic;

@Component
public class MyConfig {

    @Value("${mytopic}")
    private String topicName;

    @Bean
    public Topic topic(){
        return new ActiveMQTopic(topicName);
    }
}
