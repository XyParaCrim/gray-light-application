package gray.light.application.book.scheduler;

//import gray.lighjt.document.scheduler.annotation.JobCheckDocument;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

// @JobCheckDocument
// @EnableFeignClients
@SpringBootApplication
public class BookSchedulerApplication {

    public static void main(String[] args) {
        SpringApplication.run(BookSchedulerApplication.class, args);
    }

}
