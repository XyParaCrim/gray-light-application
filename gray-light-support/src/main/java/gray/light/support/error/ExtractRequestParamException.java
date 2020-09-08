package gray.light.support.error;

import perishing.constraint.web.KnownBusinessException;
import perishing.constraint.web.ResponseCode;

/**
 * 从请求提取参数时，发生异常，一般是类型无法转换
 *
 * @author XyParaCrim
 */
public class ExtractRequestParamException extends KnownBusinessException {

    public ExtractRequestParamException(String message) {
        super(ResponseCode.CommonResponseCode.ERROR_INTERNAL, message);
    }

    public ExtractRequestParamException(String message, Throwable cause) {
        super(ResponseCode.CommonResponseCode.ERROR_INTERNAL, message, cause);
    }

    public ExtractRequestParamException(Throwable cause) {
        super(ResponseCode.CommonResponseCode.ERROR_INTERNAL, cause);
    }
}
