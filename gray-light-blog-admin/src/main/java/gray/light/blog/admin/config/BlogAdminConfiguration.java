package gray.light.blog.admin.config;

import gray.light.blog.admin.router.BlogAdminLocalOptionsRouter;
import org.springframework.context.annotation.Import;

/**
 * 模块配置文件
 *
 * @author XyParaCrim
 */
@Import(BlogAdminLocalOptionsRouter.class)
public class BlogAdminConfiguration {
}
