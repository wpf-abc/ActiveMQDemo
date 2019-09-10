package wpf.example.activemq.bootactivemqtopicconsumer.consumer;

import org.springframework.jms.annotation.JmsListener;
import org.springframework.stereotype.Component;

import javax.jms.JMSException;
import javax.jms.TextMessage;

@Component
public class Consumer_Topic {
    @JmsListener(destination = "${mytopic}")
    public void receive(TextMessage textMessage) throws JMSException {
        System.out.println("received msg in boot: " + textMessage.getText());
    }
}
