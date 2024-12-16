package com.dhhan.customFramework.utils;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

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

    public static ObjectMapper getINSTANCE() {
        return lazyHolderObjectMapper.INSTANCE;
    }

    private static class lazyHolderObjectMapper {
        private static final ObjectMapper INSTANCE = new ObjectMapper();
    }
}
