package gray.light.config;

import floor.persistent.plugins.annotation.FloorDefaultMybatisPlugins;
import gray.light.blog.annotation.DomainBlog;
import gray.light.book.annotation.HighDomainBook;
import gray.light.document.annotation.DomainDocument;
import gray.light.note.annotation.DomainNote;
import gray.light.owner.annotation.DomainOwner;
import lombok.RequiredArgsConstructor;
import org.springframework.boot.autoconfigure.AutoConfigureAfter;
import org.springframework.context.annotation.Configuration;

/**
 * 统一配置所有模块依赖
 *
 * @author XyParaCrim
 */
@Configuration
public class ModuleDependenciesConfiguration {

    @Configuration
    //@FloorRepository
    //@FloorFileStorage
    @FloorDefaultMybatisPlugins
    public static class FloorComponentConfiguration {

    }

    @Configuration
    @AutoConfigureAfter(FloorComponentConfiguration.class)
    @RequiredArgsConstructor

    @HighDomainBook

    @DomainOwner
    @DomainBlog
    @DomainNote
    @DomainDocument
    public static class GrayLightDomainConfiguration {

    }

}
