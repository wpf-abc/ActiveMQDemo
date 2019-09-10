package wpf.example.activemq.bootactivemqtopicproducer;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.scheduling.annotation.EnableScheduling;

@EnableScheduling
@SpringBootApplication
public class BootActivemqTopicProducerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BootActivemqTopicProducerApplication.class, args);
    }

}
