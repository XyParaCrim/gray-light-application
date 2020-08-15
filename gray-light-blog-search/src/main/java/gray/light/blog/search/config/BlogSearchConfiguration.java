package gray.light.blog.search.config;

import floor.cache.RedisChannelCache;
import floor.cache.StringChannelCache;
import gray.light.blog.search.handler.BlogSearchHandler;
import gray.light.blog.search.options.BlogSearchOptions;
import gray.light.blog.search.router.PersonalSearchBlogRouter;
import gray.light.blog.search.service.SearchBlogService;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Import;
import org.springframework.data.redis.core.RedisTemplate;

@Import({
        BlogSearchHandler.class,
        SearchBlogService.class,
        BlogSearchOptions.class,
        PersonalSearchBlogRouter.class
})
public class BlogSearchConfiguration {

    @Bean
    public StringChannelCache searchChannelCache(RedisTemplate<String, String> redisTemplate) {
        return new RedisChannelCache(redisTemplate);
    }
}
