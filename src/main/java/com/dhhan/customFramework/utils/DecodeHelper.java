package com.dhhan.customFramework.utils;

import java.util.Base64;

public class DecodeHelper {
    public static String decode(String encoded) {
        return new String(Base64.getDecoder().decode(encoded));
    }
}
