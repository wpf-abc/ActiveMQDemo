package wpf.example.activemq.commons;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;
import java.io.IOException;

public class JmsConsumer_Queue {

    public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException, IOException {
        System.out.println("2号消费者");
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消费者
        MessageConsumer messageConsumer = session.createConsumer(queue);

        /*
        receive()获取到消息之前一直阻塞或阻塞指定的超时时间。

        while (true){
            //一直等
            TextMessage textMessage = (TextMessage)messageConsumer.receive();
            //messageConsumer.receive(3000);    //等3秒
            if (textMessage != null){
                System.out.println("消费者收到内容：" + textMessage.getText());
            }else {
                break;
            }
        }
        messageConsumer.close();
        session.close();
        connection.close();*/

        //通过监听的方式：
        messageConsumer.setMessageListener(new MessageListener() {
            @Override
            public void onMessage(Message message) {
                if (message != null && message instanceof TextMessage){
                    TextMessage textMessage = (TextMessage) message;
                    try {
                        System.out.println("消费者收到内容：" + textMessage.getText());
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

        /*
        * 1.先生产，再先启动一号消费者，后启动二号消费者。
        *   一号可消费、二号没有消息可消费
        * 2.先启动两个消费者，再启动生产者。
        *   一人一半，有点轮询 负载均衡的意思
        * */
    }
}
