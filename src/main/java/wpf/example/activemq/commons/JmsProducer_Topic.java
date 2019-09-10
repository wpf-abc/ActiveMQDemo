package wpf.example.activemq.commons;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 一对多
 */
public class JmsProducer_Topic {
    public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    private static final String TOPIC_NAME = "topic-test";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageProducer messageProducer = session.createProducer(topic);
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("msg--->" + i);
            //设置消息属性
            textMessage.setStringProperty("SuperMan", "vip");
            //textMessage.setBooleanProperty(, );
            //textMessage.setFloatProperty(, );
            //textMessage.setIntProperty(, );

            /*消息头
            //目的地
            textMessage.setJMSDestination(topic);
            //持久或非持久模式
            textMessage.setJMSDeliveryMode(DeliveryMode.PERSISTENT);
            //消息生存时间，默认是0，即永不过期
            textMessage.setJMSExpiration(3000);
            //优先级；可取0-9，0-4为普通消息，5-9为加急消息，默认是4。加急消息比普通消息先到达
            textMessage.setJMSPriority(4);
            //唯一识别每个消息的标识，由MQ产生也可以自己指定
            textMessage.setJMSMessageID();*/

            messageProducer.send(textMessage);
        }

        MapMessage mapMessage = session.createMapMessage();
        mapMessage.setString("k1", "v1");
        messageProducer.send(mapMessage);

        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("发送完成");
    }
}
