package com.dhhan.customFramework.utils;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;

import java.util.HashMap;

public class JsonHelper {
    public static String parseObjectToJson(Object object) {
        try {
            return lazyHolderObjectMapper.INSTANCE.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static HashMap convertToMap(String string) {
        try {
            return lazyHolderObjectMapper.INSTANCE.readValue(string,HashMap.class);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static <T> T convertToClass(String string, Class<T> type) {
        try {
            return lazyHolderObjectMapper.INSTANCE.readValue(string, type);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    public static ObjectMapper getINSTANCE() {
        return lazyHolderObjectMapper.INSTANCE;
    }

    private static class lazyHolderObjectMapper {
        private static final ObjectMapper INSTANCE =createConfiguredObjectMapper();

        private static ObjectMapper createConfiguredObjectMapper() {
            ObjectMapper objectMapper = new ObjectMapper();
            // ObjectMapper 설정 추가
            objectMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false); // 예시: 알 수 없는 속성 무시
            objectMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL); // 예시: null 값 제외
            //objectMapper.registerModule(new JavaTimeModule()); // 예시: Java 8 날짜 및 시간 처리 모듈 등록
            return objectMapper;
        }
    }
}
