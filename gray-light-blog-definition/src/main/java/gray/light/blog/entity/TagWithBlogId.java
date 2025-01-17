package gray.light.blog.entity;

import lombok.Getter;
import lombok.Setter;
import org.apache.ibatis.type.Alias;

import java.util.Date;

/**
 * tag信息加上所属博客Id信息
 *
 * @author XyParaCrim
 */
@Alias("TagWithBlogId")
public class TagWithBlogId extends Tag {

    public TagWithBlogId(String name, String color, Date createdDate, Date updatedDate, Long blogId) {
        super(name, color, createdDate, updatedDate);
        this.blogId = blogId;
    }

    @Getter
    @Setter
    private Long blogId;

}
