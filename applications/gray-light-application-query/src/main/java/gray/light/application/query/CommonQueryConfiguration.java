package gray.light.application.query;

import floor.persistent.plugins.annotation.FloorDefaultMybatisPlugins;
import gray.light.blog.annotation.DomainBlog;
import gray.light.book.annotation.HighDomainBook;
import gray.light.document.annotation.DomainDocument;
import gray.light.note.annotation.DomainNote;
import gray.light.owner.annotation.DomainOwner;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.web.reactive.config.CorsRegistry;
import org.springframework.web.reactive.config.EnableWebFlux;
import org.springframework.web.reactive.config.WebFluxConfigurer;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.WebSession;
import reactor.core.publisher.Mono;

@HighDomainBook

@DomainOwner
@DomainBlog
@DomainNote
@DomainDocument
@Configuration
@FloorDefaultMybatisPlugins
public class CommonQueryConfiguration {

    private static class SessionFilter implements WebFilter {

        @Override
        public Mono<Void> filter(ServerWebExchange exchange, WebFilterChain chain) {
            return exchange.
                    getSession().
                    doOnSuccess(WebSession::start).
                    then(chain.filter(exchange));
        }

    }

    @Bean
    public WebFilter sessionFilter() {
        return new SessionFilter();
    }

    @Configuration
    @EnableWebFlux
    public static class GlobCorsConf implements WebFluxConfigurer {

        @Override
        public void addCorsMappings(CorsRegistry registry) {
            registry.addMapping("/**")
                    .allowCredentials(false)
                    .allowedOrigins("*")
                    .allowedHeaders("*")
                    .allowedMethods("*")
                    .exposedHeaders(HttpHeaders.SET_COOKIE);
        }
    }

}
