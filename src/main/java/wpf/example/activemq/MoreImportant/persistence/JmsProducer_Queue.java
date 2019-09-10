package wpf.example.activemq.MoreImportant.persistence;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 将消息持久到 MySQL
 */
public class JmsProducer_Queue {

    public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    private static final String QUEUE_NAME = "jdbcTest01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);

        //保存到文件或者数据库，这里配置了MySQL所以是保存到数据库
        messageProducer.setDeliveryMode(DeliveryMode.PERSISTENT);
        //保存到内存
        //messageProducer.setDeliveryMode(DeliveryMode.NON_PERSISTENT);

        for (int i = 1; i <= 6; i++) {
            TextMessage textMessage = session.createTextMessage("jdbc msg--->" + i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("发送完成");
    }
}
