package com.example.resp.utils;

import java.util.Locale;

public class StringUtils {
    /**
     * 将传入的字符串的第一个字母变为大写(不是首字母变大写)
     * @param string 需要将第一个字母大写的字符串
     * @return 返回只有第一个字母大写的字符串
     * */
    public static String uppercaseFirst(String string) {
        //传入字符串不为空则进行首字母变为大写
        if (string != null) {
            return Character.toUpperCase(string.charAt(0)) + string.substring(1);
        }
        return null;
    }
}
