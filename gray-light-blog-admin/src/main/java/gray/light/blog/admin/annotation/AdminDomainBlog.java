package gray.light.blog.admin.annotation;

import gray.light.blog.admin.config.BlogAdminConfiguration;
import org.springframework.context.annotation.Import;

import java.lang.annotation.*;

/**
 * 引入该模块配置
 *
 * @author XyParaCrim
 */
@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.TYPE)
@Documented
@Import(BlogAdminConfiguration.class)
public @interface AdminDomainBlog {
}
