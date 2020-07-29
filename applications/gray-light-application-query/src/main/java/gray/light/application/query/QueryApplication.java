package gray.light.application.query;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

@Configuration
@EnableRedisWebSession
@SpringBootApplication
public class QueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryApplication.class);
    }

}
