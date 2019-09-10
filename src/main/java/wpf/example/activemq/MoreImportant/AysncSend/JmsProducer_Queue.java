package wpf.example.activemq.MoreImportant.AysncSend;

import org.apache.activemq.ActiveMQConnectionFactory;
import org.apache.activemq.ActiveMQMessageProducer;
import org.apache.activemq.AsyncCallback;

import javax.jms.*;
import java.util.UUID;

/**
 * 快生产，慢消费
 * 异步发送
 */
public class JmsProducer_Queue {

    public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        factory.setUseAsyncSend(true);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);

        ActiveMQMessageProducer activeMQMessageProducer = (ActiveMQMessageProducer)session.createProducer(queue);

        for (int i = 1; i <= 6; i++) {
            TextMessage textMessage = session.createTextMessage("msg--->" + i);
            textMessage.setJMSMessageID(UUID.randomUUID().toString()+"---xxxx");
            String msgID = textMessage.getJMSMessageID();
            activeMQMessageProducer.send(textMessage, new AsyncCallback() {
                @Override
                public void onSuccess() {
                    System.out.println(msgID + " has been sent");
                }

                @Override
                public void onException(JMSException e) {
                    System.out.println(msgID + " flailed");
                }
            });
        }
        activeMQMessageProducer.close();
        session.close();
        connection.close();

        System.out.println("发送完成");
    }
}
