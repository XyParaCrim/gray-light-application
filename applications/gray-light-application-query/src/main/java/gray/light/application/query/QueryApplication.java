package gray.light.application.query;

import floor.coordination.FloorConfigWatch;
import floor.coordination.FloorCoordination;
import lombok.extern.slf4j.Slf4j;
import org.apache.curator.framework.CuratorFramework;
import org.apache.zookeeper.data.Stat;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.session.data.redis.config.annotation.web.server.EnableRedisWebSession;

@Configuration
@EnableRedisWebSession
@SpringBootApplication
@Slf4j
public class QueryApplication {

    public static void main(String[] args) {
        SpringApplication.run(QueryApplication.class);
    }


    @Bean
    public FloorConfigWatch initDynamic(CuratorFramework zookeeperClient) throws Exception {


        Stat stat = zookeeperClient.checkExists().forPath("/schedule/zzzz");

        log.error(stat == null ? "null" : stat.toString());

        return FloorCoordination.matchNodeAdd("/schedule", "zzz", zookeeperClient, System.out::print);
    }

}
