package gray.light.owner.config;

import org.mybatis.spring.annotation.MapperScan;

/**
 * owner领域自动配置
 *
 * @author XyParaCrim
 */
@MapperScan(OwnerAutoConfiguration.MAPPER_PACKAGE)
public class OwnerAutoConfiguration {

    public static final String MAPPER_PACKAGE = "gray.light.owner.repository";

}
