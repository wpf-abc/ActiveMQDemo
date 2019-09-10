package wpf.example.activemq.MoreImportant.DelayAndScheduledSend;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ScheduledMessage;

import javax.jms.*;

/**
 * 延时投递和定时投递
 */
public class JmsProducer_Queue {

    public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);

        long delay = 3 * 1000;  //延时三秒
        long period = 4 * 1000; //每四秒投一次
        int repeat = 5;         //重复五次

        for (int i = 1; i <= 6; i++) {
            TextMessage textMessage = session.createTextMessage("msg--->" + i);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_DELAY, delay);
            textMessage.setLongProperty(ScheduledMessage.AMQ_SCHEDULED_PERIOD, period);
            textMessage.setIntProperty(ScheduledMessage.AMQ_SCHEDULED_REPEAT, repeat);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("发送完成");
    }
}
