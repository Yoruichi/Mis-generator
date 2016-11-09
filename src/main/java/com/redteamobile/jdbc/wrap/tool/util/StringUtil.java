package com.redteamobile.jdbc.wrap.tool.util;

/**
 * 字符串实用工具类
 */
public class StringUtil {
    public static String firstLower(String s) {
        String first = s.substring(0, 1);
        String other = s.substring(1, s.length());
        return first.toLowerCase() + other;
    }

    public static String firstUpper(String s) {
        String first = s.substring(0, 1);
        String other = s.substring(1, s.length());
        return first.toUpperCase() + other;
    }
}
