package wpf.example.activemq.tx;

import org.apache.activemq.ActiveMQConnection;
import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_Queue_tx {

    //public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    public static final String ACTIVEMQ_URL="tcp://localhost:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        factory.setUseAsyncSend(true);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.CLIENT_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("消费者收到内容：" + textMessage.getText());
                        //手动签收。当事务没开启且Session.CLIENT_ACKNOWLEDGE
                        textMessage.acknowledge();
                        //事务开启时，只有执行了commit才会签收，无论session是什么模式
                        session.commit();
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
