package gray.light.support.web;

import perishing.constraint.treasure.chest.collection.FinalVariables;

import java.util.Map;

/**
 * 通过字符串映射的参数表
 *
 * @author XyParaCrim
 */
public class RequestParamVariables extends FinalVariables<String> {

    public RequestParamVariables(Map<String, Object> variables) {
        super(variables);
    }

    public <V> void setBodyObject(Class<V> type, V value) {
        set(type.getName(), value);
    }

    public <V> V getBodyObject(Class<V> type) {
        return get(type.getName());
    }
}
