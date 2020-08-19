package gray.light.support.web;

import lombok.*;

/**
 * 定义服务器回复格式
 *
 * @author XyParaCrim
 */
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseFormat {

    // TODO 因为foreign修改属性值final

    @Getter
    @Setter
    private int code;

    @Getter
    @Setter
    private Object data;

    @Getter
    @Setter
    private String msg = "";

}
