package wpf.example.activemq.commons;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 一对一
 */
public class JmsProducer_Queue {

    public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        //1.用指定的URL创建连接工厂。用户名密码默认都是admin
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        //2.获得Connection并启动
        Connection connection = factory.createConnection();
        connection.start();
        //3.创建session，参数一：事务，参数二：签收
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        //4.创建目的地（具体是队列（queue）还是主题（topic））
        //Destination有两个子接口：Queue 和 Topic
        Queue queue = session.createQueue(QUEUE_NAME);
        //5.创建消息生产者
        MessageProducer messageProducer = session.createProducer(queue);
        //6.通过消息生产者发送3条消息
        for (int i = 1; i <= 6; i++) {
            //7.创建消息
            TextMessage textMessage = session.createTextMessage("msg--->" + i);
            //8.发送消息
            messageProducer.send(textMessage);
        }
        //9.关闭资源
        messageProducer.close();
        session.close();
        connection.close();

        System.out.println("发送完成");
    }
}
