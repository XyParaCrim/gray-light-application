package gray.light.support.error;

/**
 * 请求表单类型转换错误
 *
 * @author XyParaCrim
 */
public class NormalizingFormException extends KnownBusinessException {

    public NormalizingFormException(String message) {
        super(message);
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
