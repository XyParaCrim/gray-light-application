package gray.light.document.config;

import org.mybatis.spring.annotation.MapperScan;

/**
 * 关于作品文档的所有配置
 *
 * @author XyParaCrim
 */
@MapperScan(DocumentAutoConfiguration.MAPPER_PACKAGE)
public class DocumentAutoConfiguration {

    public static final String MAPPER_PACKAGE = "gray.light.document.repository";

}
