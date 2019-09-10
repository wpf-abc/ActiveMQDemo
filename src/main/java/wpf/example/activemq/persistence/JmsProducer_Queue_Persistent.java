package wpf.example.activemq.persistence;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 一对一
 */
public class JmsProducer_Queue_Persistent {

    public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        //设置为持久
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);

        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("msg--->" + i);
            messageProducer.send(textMessage);
        }

        TextMessage nonPersistentMsg = session.createTextMessage("Non_persistent msg");
        //经测验发现：messageProducer设置为持久，这里将单独一条消息设置为非持久不会生效
        nonPersistentMsg.setJMSDeliveryMode(DeliveryMode.NON_PERSISTENT);
        messageProducer.send(nonPersistentMsg);

        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("发送完成");
    }
}
