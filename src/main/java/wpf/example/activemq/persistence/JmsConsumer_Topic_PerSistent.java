package wpf.example.activemq.persistence;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

/**
 * 需要先启动即消费者，消费者只会收到订阅时间之后的消息，不会收到订阅之前的消息。
 * 没有订阅者的话，生产者发布的消息就是废消息
 *
 * 订阅者先启动一次，相当于订阅了这个主题；之后发布的消息，订阅者一定会收到。如果不在线
 *  那么下次连接的时候会收到离线期间的全部消息
 */
public class JmsConsumer_Topic_PerSistent {
    public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    private static final String TOPIC_NAME = "topic-persistent";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        //设置客户端的id
        connection.setClientID("z3");

        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Topic topic = session.createTopic(TOPIC_NAME);

        //第二个参数是订阅名称
        TopicSubscriber topicSubscriber = session.createDurableSubscriber(topic, "tips......");

        //到这里才启动连接
        connection.start();
        System.out.println("ok");

        Message message = topicSubscriber.receive();
        while (message != null){
            TextMessage textMessage = (TextMessage)message;
            System.out.println("持久化topic："+textMessage.getText());
            message = topicSubscriber.receive(2000L);
        }

        session.close();
        connection.close();
    }
}
