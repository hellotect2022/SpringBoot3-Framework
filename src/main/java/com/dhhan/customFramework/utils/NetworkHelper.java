package com.dhhan.customFramework.utils;

import jakarta.servlet.http.HttpServletRequest;

public class NetworkHelper {

    public static String getRemoteIP(HttpServletRequest request) {
        // TODO: Implement logic to get IP address
        String ip = request.getHeader("X-Forwarded-For");
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("WL-Proxy-Client-IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_CLIENT_IP");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getHeader("HTTP_X_FORWARDED_FOR");
        }
        if (ip == null || ip.length() == 0 || "unknown".equalsIgnoreCase(ip)) {
            ip = request.getRemoteAddr();
        }

        return ip;
    }

    public static String getClientType(HttpServletRequest request) {
        try {
            String user_agent = request.getHeader("Device-Type");
            if (user_agent != null) {
                return user_agent;
            }else {
                return "browser";
            }
        }catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
