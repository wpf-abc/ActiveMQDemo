package wpf.example.activemq.bootactivemqproducer;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;
import wpf.example.activemq.bootactivemqproducer.producer.Producer_Queue;

@RunWith(SpringRunner.class)
@SpringBootTest
public class BootActivemqProducerApplicationTests {

    @Autowired
    private Producer_Queue producer_queue;

    @Test
    public void contextLoads() {
    }

    @Test
    public void test01(){
        //producer_queue.produceMsg();
        producer_queue.prduceceMsgScheduled();
    }
}
