package gray.light.note.service;

import gray.light.definition.entity.Scope;
import gray.light.owner.entity.OwnerProject;
import gray.light.owner.entity.ProjectDetails;
import gray.light.owner.entity.ProjectStatus;
import gray.light.owner.service.OverallOwnerService;
import gray.light.owner.service.ProjectDetailsService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import perishing.constraint.jdbc.Page;

import java.util.List;

/**
 * 笔记服务
 *
 * @author XyParaCrim
 */
@RequiredArgsConstructor
public class WritableNoteService {

    private final ProjectDetailsService projectDetailsService;

    /**
     * 创建一个笔记项目并追踪它
     *
     * @param noteProject 笔记项目
     * @param uncommited  项目详细
     * @return 是否创建成功
     */
    public boolean createNote(OwnerProject noteProject, ProjectDetails uncommited) {
        return projectDetailsService.addBookProjectDetailsSafely(noteProject, Scope.NOTE, uncommited);
    }

}
