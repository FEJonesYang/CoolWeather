package com.jonesyong.library_common.utils;

/**
 * @Author JonesYang
 * @Date 2022-01-10
 * @Description 一般工具类，例如判断字符串是否相同
 */
public class CommonUtil {

    /**
     * @param s 判断字符
     * @return true 不为null，且长度有效
     */
    public static boolean isStringValid(String s) {
        if (s == null) {
            return false;
        }
        return s.length() != 0;
    }

    /**
     * @return true 两个字符串内容相同
     */
    public static boolean isStringEquals(String s1, String s2) {
        return s1.equals(s2);
    }
}
