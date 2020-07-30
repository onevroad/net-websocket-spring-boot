package org.net.websocket.core;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;

public class ObjectConvert {

    public static <T> T convert(Object source, Class<T> clazz) {
        return JSON.parseObject(toJson(source), clazz);
    }

    /**
     * 支持泛型转换
     * @param source 源
     * @param type 目标类型 new TypeReference<T>().getType;
     * For example:
     * <pre>
     * Type type = new TypeReference<Result<Foo>>(){}.getType();
     * </pre>
     * @return 目标实例对象
     */
    public static <T> T convert(Object source, Type type) {
        return JSON.parseObject(JSON.toJSONString(source), type);
    }

    public static <T> T convert(String data, Class<T> clazz) {
        return JSON.parseObject(data, clazz);
    }

    public static <T> T convert(String data, Type type) {
        return JSON.parseObject(data, type);
    }

    public static Object convertRequestData(Object data, Class<?> handlerClass) {
        Class<?> dataClass = Object.class;
        Type[] genericInterfaces = handlerClass.getGenericInterfaces();
        if (genericInterfaces.length > 0) {
            Type genericInterface = genericInterfaces[0];
            if (genericInterface instanceof ParameterizedType) {
                ParameterizedType parameterizedType = (ParameterizedType) genericInterface;
                dataClass = (Class<?>) parameterizedType.getActualTypeArguments()[0];
                if (data.getClass().getName().equals(dataClass.getName())) {
                    return data;
                }
            }
        }
        return convert(data, dataClass);
    }

    public static String toJson(Object data) {
        if (data instanceof String) {
            return (String) data;
        }
        return JSON.toJSONString(data);
    }
}
