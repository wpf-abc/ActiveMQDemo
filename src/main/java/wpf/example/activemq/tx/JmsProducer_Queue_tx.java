package wpf.example.activemq.tx;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 一对一
 */
public class JmsProducer_Queue_tx {

    //public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    public static final String ACTIVEMQ_URL="tcp://localhost:61616";
    private static final String QUEUE_NAME = "queue01";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(true, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        for (int i = 1; i <= 6; i++) {
            TextMessage textMessage = session.createTextMessage("msg--->" + i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();

        session.commit();//事务提交
        //session.rollback(); 出错后，try catch结构

        session.close();
        connection.close();

        System.out.println("发送完成");
    }
}
