package gray.light.support.error;

import perishing.constraint.web.KnownBusinessException;
import perishing.constraint.web.ResponseCode;

/**
 * 请求表单类型转换错误
 *
 * @author XyParaCrim
 */
public class NormalizingFormException extends KnownBusinessException {

    public NormalizingFormException(String message) {
        super(ResponseCode.CommonResponseCode.ERROR_INTERNAL, message);
    }

    /**
     * 请求表单类型转换过程中，key属性缺失
     *
     * @param key key属性缺失
     */
    public static void emptyProperty(String key) {
        throw new NormalizingFormException("The property's value of " + key + " is empty.");
    }
}
