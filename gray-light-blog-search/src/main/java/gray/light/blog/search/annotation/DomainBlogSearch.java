package gray.light.blog.search.annotation;

import gray.light.blog.search.config.BlogSearchConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 关于blog搜索领域的自动配置
 *
 * @author XyParaCrim
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(BlogSearchConfiguration.class)
public @interface  DomainBlogSearch {
}
