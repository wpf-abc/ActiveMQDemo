package wpf.example.activemq.commons;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 需要先启动即消费者，消费者只会收到订阅时间之后的消息，不会收到订阅之前的消息。
 * 没有订阅者的话，生产者发布的消息就是废消息
 */
public class JmsConsumer_Topic {
    public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    private static final String TOPIC_NAME = "topic-test";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("2号消费者");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);
        MessageConsumer messageConsumer = session.createConsumer(topic);

        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage) {
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("消费者收到内容：" + textMessage.getText());
                        System.out.println("消费者收到内容的属性：" + textMessage.getStringProperty("SuperMan"));
                    } catch (JMSException e) {
                        e.printStackTrace();
                    }
                }
            }
        });
        System.in.read();   //阻塞，否则还没消费到消息资源就被关闭了
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
