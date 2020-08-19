package gray.light.owner.definition.entity;


import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import org.apache.ibatis.type.Alias;

import java.io.Serializable;
import java.util.Date;

/**
 * 只记录关于GIT项目的信息
 *
 * @author XyParaCrim
 */
@Data
@Alias("ProjectDetails")
@Builder
@AllArgsConstructor
public class ProjectDetails implements Serializable {

    private static final long serialVersionUID = 1L;

    private Long originId;

    private String type;

    private String http;

    private String version;

    private Date createdDate;

    private Date updatedDate;

    @Builder.Default
    private ProjectStatus status = ProjectStatus.WAIT_PERSISTENCE;

    @Builder.Default
    private ProjectStructure structure = ProjectStructure.UNKNOWN;

}
