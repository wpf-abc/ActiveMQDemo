package wpf.example.activemq.MoreImportant.protocol;

import org.apache.activemq.ActiveMQConnectionFactory;

import javax.jms.*;

/**
 * 关于协议：
 *  参看md笔记。
 */

//tips：nio 和 auto+nio 在配置文件种已经注释了，测试需打开
public class JmsProducer_Queue {

    //tcp，tcp协议
    //public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61616";
    //nio，nio协议
    //public static final String ACTIVEMQ_URL="nio://192.168.9.3:61618";

    //auto+nio，自动转换
    //public static final String ACTIVEMQ_URL="nio://192.168.9.3:61608";
    //auto+tcp，仅仅只需要改协议，不需要改端口
    public static final String ACTIVEMQ_URL="tcp://192.168.9.3:61608";
    private static final String QUEUE_NAME = "auto-tcp";

    public static void main(String[] args) throws JMSException {
        ActiveMQConnectionFactory factory = new ActiveMQConnectionFactory(ACTIVEMQ_URL);
        Connection connection = factory.createConnection();
        connection.start();
        Session session = connection.createSession(false, Session.AUTO_ACKNOWLEDGE);
        Queue queue = session.createQueue(QUEUE_NAME);
        MessageProducer messageProducer = session.createProducer(queue);
        for (int i = 1; i <= 3; i++) {
            TextMessage textMessage = session.createTextMessage("msg--->" + i);
            messageProducer.send(textMessage);
        }
        messageProducer.close();
        session.close();
        connection.close();
        System.out.println("发送完成");
    }
}
