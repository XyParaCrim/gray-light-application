package gray.light.stream.book.source;

import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.cloud.stream.annotation.EnableBinding;

/**
 * 引入book stream信息的配置
 *
 * @author XyParaCrim
 */
@EnableBinding(SynchronizedBookSource.class)
@EnableConfigurationProperties(BookSourceProperties.class)
public class BookSourceConfiguration {

}
