package gray.light.document.business;

import gray.light.owner.business.OwnerProjectFo;
import gray.light.owner.business.ProjectDetailsFo;
import gray.light.support.error.NormalizingFormException;
import lombok.Data;

/**
 * 创建文档的表单
 *
 * @author XyParaCrim
 */
@Data
public class DocumentFo {

    private Long worksId;

    private OwnerProjectFo document;

    private ProjectDetailsFo source;

    /**
     * 对请求表单进行标准化和检查
     *
     * @throws NormalizingFormException 表单属性不合格的时候
     */
    public void normalize() throws NormalizingFormException {
        if (worksId == null) {
            NormalizingFormException.emptyProperty("worksId");
        }

        if (document == null) {
            NormalizingFormException.emptyProperty("document");
        }
        document.normalize();

        if (source == null) {
            NormalizingFormException.emptyProperty("source");
        }
        source.normalize();
    }


}
