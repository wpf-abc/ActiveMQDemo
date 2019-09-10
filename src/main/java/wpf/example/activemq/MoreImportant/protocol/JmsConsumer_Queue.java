package wpf.example.activemq.MoreImportant.protocol;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_Queue {

    //tcp，tcp协议
    //public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    //nio，nio协议
    //public static final String ACTIVEMQ_URL="nio://192.168.9.3:61618";

    //auto+nio，自动转换
    //public static final String ACTIVEMQ_URL="nio://192.168.9.3:61608";
    //auto+tcp，仅仅只需要改协议，不需要改端口
    public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61608";
    private static final String QUEUE_NAME = "auto-tcp";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageConsumer messageConsumer = session.createConsumer(queue);
        while (true){
            TextMessage textMessage = (TextMessage)messageConsumer.receive();
            if (textMessage != null){
                System.out.println("消费者收到内容：" + textMessage.getText());
            }else {
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();
    }
}
