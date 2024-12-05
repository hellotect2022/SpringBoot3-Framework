package com.dhhan.customFramework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

public class JsonHelper {
    public static String parseObjectToJson(Object object) {
        try {
            return lazyHolderObjectMapper.INSTANCE.writeValueAsString(object);
        } catch (JsonProcessingException e) {
            throw new RuntimeException(e);
        }
    }

    private static class lazyHolderObjectMapper {
        private static final ObjectMapper INSTANCE = new ObjectMapper();
    }
}
