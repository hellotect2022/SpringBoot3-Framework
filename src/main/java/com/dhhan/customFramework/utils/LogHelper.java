package com.dhhan.customFramework.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.slf4j.Marker;
import org.slf4j.MarkerFactory;

import java.lang.reflect.Field;
import java.util.List;
import java.util.Map;

public class LogHelper {

    public static Marker marker = MarkerFactory.getMarker("STDOUT");

    public static void debug(String msg, Object ref) {
        Logger LOGGER = LoggerFactory.getLogger(ref.getClass());
        LOGGER.debug(marker,"({}) # {}", ref.getClass().getName(),msg);
    }

    public static void info(String msg, Object ref) {
        Logger LOGGER = LoggerFactory.getLogger(ref.getClass());
        LOGGER.info(marker,"({}) # {}", ref.getClass().getName(),msg);
    }

    public static void info(Object obj, Object ref) {
        Logger LOGGER = LoggerFactory.getLogger(ref.getClass());
        if (obj != null) {
            LOGGER.info("========== {} ==========", obj.getClass().getName());
            LOGGER.info("{}",checkObject(obj));
        }
    }

    public static void error(Exception e) {
        Logger LOGGER = LoggerFactory.getLogger(LogHelper.class);
        StackTraceElement[] stackTraces2 = new Throwable().getStackTrace();
        LOGGER.error(stackTraces2[0].toString());
        LOGGER.error(stackTraces2[1].toString());


        LOGGER.error("Error occurred: {}", e.getMessage());
        int loop  = e.getStackTrace().length;
                //> 10 ? 10: e.getStackTrace().length;
        for (int i = 0; i < loop; i++) {
            LOGGER.error("-> " + e.getStackTrace()[i]);
        }
    }




    private static String checkObject(Object obj) {
        if (obj instanceof Map) {
            return encloseMap(obj);
        }else if (obj instanceof List) {
            return encloseList(obj);
        }else {
            return encloseObject(obj);
        }
    }

    private static String encloseMap(Object obj) {
            Map<?, ?> map = (Map<?, ?>) obj; // Map으로 타입 캐스팅
            StringBuilder str= new StringBuilder();
            str.append("List Item: {");
            for (Map.Entry<?, ?> entry : map.entrySet()) {
                Object key = entry.getKey();
                String value = checkObject(entry.getValue());
                str.append(key).append(": ").append(value).append(", "); // 필드 이름과 값 출력
            }
            str.append("}");
            return str.toString();
    }

    private static String encloseList(Object obj) {
        List<?> list = (List<?>) obj; // List로 타입 캐스팅
        StringBuilder str= new StringBuilder();
        str.append("List Item: {");
        for (Object item : list) {
            str.append(checkObject(item)).append(", ");
        }
        str.append("}");
        return str.toString();
    }

    private static String encloseObject(Object obj) {
        if (obj.getClass().getPackage().getName().endsWith(".dto")) {
            Field[] fields = obj.getClass().getDeclaredFields();
            StringBuilder str= new StringBuilder();
            str.append("{");
            for (Field field : fields) {
                field.setAccessible(true); // private 필드 접근
                String fieldName = field.getName();
                Object fieldValue = null;
                try {
                    fieldValue = field.get(obj);
                } catch (IllegalAccessException e) {
                    throw new RuntimeException(e);
                }
                str.append(fieldName).append(": ").append(fieldValue).append(", "); // 필드 이름과 값 출력
            }
            str.append("}");
            return str.toString();
        }
        return obj.toString();
    }
}
