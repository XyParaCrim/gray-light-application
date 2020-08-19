package gray.light.blog.annotation;

import gray.light.blog.config.BlogAutoConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 关于blog领域的自动配置
 *
 * @author XyParaCrim
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(BlogAutoConfiguration.class)
public @interface DomainBlog {

    /**
     * 是否装载Blog所有服务
     *
     * @return 是否装载Blog所有服务
     */
    boolean value() default true;

    /**
     * 是否只提供可读服务
     *
     * @return 是否只提供可读服务
     */
    boolean onlyRead() default false;

    /**
     * 是否提供搜索服务
     *
     * @return 是否提供搜索服务
     */
    boolean searchService() default true;

}
