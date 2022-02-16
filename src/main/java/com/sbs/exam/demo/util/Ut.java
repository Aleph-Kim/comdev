package com.sbs.exam.demo.util;

public class Ut {

    public static boolean empty(Object obj) {
        if (obj instanceof Integer) {
            return (int) obj <= 0;
        }

        if (obj == null) {
            return true;
        }
        String str = (String) obj;

        return str.trim().length() == 0;
    }

    public static String f(String format, Object... args) {
        return String.format(format, args);
    }

}