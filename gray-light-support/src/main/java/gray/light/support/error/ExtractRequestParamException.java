package gray.light.support.error;

/**
 * 从请求提取参数时，发生异常，一般是类型无法转换
 *
 * @author XyParaCrim
 */
public class ExtractRequestParamException extends KnownBusinessException {

    public ExtractRequestParamException(String message) {
        super(message);
    }

    public ExtractRequestParamException(String message, Throwable cause) {
        super(message, cause);
    }

    public ExtractRequestParamException(Throwable cause) {
        super(cause);
    }
}
