package gray.light.support.error;

import gray.light.support.web.ResponseFormat;

/**
 * 处理业务时发生的异常且包含想要传递出去错误信息的异常
 *
 * @author XyParaCrim
 */
public class KnownBusinessException extends RuntimeException {

    public KnownBusinessException(String message) {
        super(message);
    }

    public KnownBusinessException(Throwable cause) {
        super(cause);
    }

    public KnownBusinessException(String message, Throwable cause) {
        super(message, cause);
    }

    public ResponseFormat toResponseFormat() {
        return null;
    }

}
