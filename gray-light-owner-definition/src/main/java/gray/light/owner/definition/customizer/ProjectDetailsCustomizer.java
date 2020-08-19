package gray.light.owner.definition.customizer;

import gray.light.owner.definition.business.ProjectDetailsFo;
import gray.light.owner.definition.entity.ProjectDetails;
import gray.light.owner.definition.entity.ProjectStructure;

public final class ProjectDetailsCustomizer {

    public final static String OWNER_TYPE = "owner";

    public static ProjectDetails ofNewBookFromOwner(Long originId, ProjectDetailsFo projectDetailsFo) {
        return ProjectDetails.builder().
                originId(originId).
                type(OWNER_TYPE).
                http(projectDetailsFo.getHttp()).
                structure(ProjectStructure.BOOK).
                build();
    }

    public static ProjectDetails uncommitProjectDetails(ProjectDetailsFo projectDetailsFo) {
        return ProjectDetails.
                builder().
                http(projectDetailsFo.getHttp()).
                build();
    }

    public static void completeNewBookFromOwner(ProjectDetails projectDetails, Long originId) {
        projectDetails.setOriginId(originId);
        projectDetails.setType(OWNER_TYPE);
        projectDetails.setStructure(ProjectStructure.BOOK);
    }

}
