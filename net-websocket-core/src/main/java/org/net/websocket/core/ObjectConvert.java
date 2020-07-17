package org.net.websocket.core;

import com.alibaba.fastjson.JSON;

import java.lang.reflect.Type;

public class ObjectConvert {

    public static <T> T convert(Object source, Class<T> clazz) {
        return JSON.parseObject(JSON.toJSONString(source), clazz);
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

    public static String toJson(Object data) {
        if (data instanceof String) {
            return (String) data;
        }
        return JSON.toJSONString(data);
    }
}
