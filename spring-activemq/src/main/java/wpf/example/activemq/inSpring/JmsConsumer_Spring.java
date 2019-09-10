package wpf.example.activemq.inSpring;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.context.support.ClassPathXmlApplicationContext;
import org.springframework.jms.core.JmsTemplate;
import org.springframework.stereotype.Service;

@Service
public class JmsConsumer_Spring {

    @Autowired
    JmsTemplate jmsTemplate;

    public static void main(String[] args) {
        ApplicationContext ctx = new ClassPathXmlApplicationContext("applicationConfig.xml");
        JmsConsumer_Spring consumer = (JmsConsumer_Spring) ctx.getBean("jmsConsumer_Spring");

        String receive = (String) consumer.jmsTemplate.receiveAndConvert();

        System.out.println("receive = " + receive);

    }
}
