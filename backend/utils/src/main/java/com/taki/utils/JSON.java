package com.taki.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JSON {
    private static final ObjectMapper OBJECT_MAPPER = new ObjectMapper();

    /**
     * 将对象转换为JSON字符串
     *
     * @param object 要转换的对象
     * @return 转换后的JSON字符串
     * @throws JsonProcessingException 如果转换失败
     */
    public static String toJson(Object object) {
        try {
            return OBJECT_MAPPER.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    /**
     * 将JSON字符串转换为指定类型的对象
     *
     * @param json  JSON字符串
     * @param clazz 对象类型
     * @return 转换后的对象
     * @throws JsonProcessingException 如果转换失败
     */
    public static <T> T toObject(String json, Class<T> clazz) {
        try {
            return OBJECT_MAPPER.readValue(json, clazz);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }


}
