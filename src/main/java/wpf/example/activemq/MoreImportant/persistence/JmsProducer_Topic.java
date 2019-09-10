package wpf.example.activemq.MoreImportant.persistence;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 一对多
 */
public class JmsProducer_Topic {
    public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    private static final String TOPIC_NAME = "jdbc-topic-test";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer messageProducer = session.createProducer(topic);

        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("msg--->" + i);
            textMessage.setStringProperty("SuperMan", "vip");
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("发送完成");
    }
}
