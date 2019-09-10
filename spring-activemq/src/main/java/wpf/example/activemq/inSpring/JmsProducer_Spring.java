package wpf.example.activemq.inSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.jms.core.MessageCreator;
import org.springframework.stereotype.Service;

import javax.jms.JMSException;
import javax.jms.Message;
import javax.jms.Session;
import javax.jms.TextMessage;

@Service
public class JmsProducer_Spring {

    @Autowired
    private JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationConfig.xml");
        JmsProducer_Spring producer = (JmsProducer_Spring) ctx.getBean("jmsProducer_Spring");

        //目的地是主题还是队列是由配置文件指定的，代码都一样
        producer.jmsTemplate.send(new MessageCreator() {
            @Override
            public Message createMessage(Session session) throws JMSException {
                TextMessage textMessage = session.createTextMessage("ActiveMQ in Spring MSG.......");
                return textMessage;
            }
        });

        System.out.println("send ok ...");

    }
}
