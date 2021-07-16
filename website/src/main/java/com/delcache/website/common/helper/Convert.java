package com.delcache.website.common.helper;

import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class Convert {

    public static int parseInt(Object str) {
        try {
            if (str == null || "".equals(str)) {
                return 0;
            }
            return Integer.parseInt(str.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static long parseLong(Object str) {

        try {
            if (str == null || "".equals(str)) {
                return 0;
            }
            return Long.parseLong(str.toString());
        } catch (Exception e) {
            return 0;
        }
    }

    public static String listToString(List list, String separator) {
        StringBuilder sb = new StringBuilder();
        for (int i = 0; i < list.size(); i++) {
            if (i == list.size() - 1) {
                sb.append(list.get(i));
            } else {
                sb.append(list.get(i));
                sb.append(separator);
            }
        }
        return sb.toString();
    }

    public static String listToString(List list) {
        return listToString(list, ",");
    }

    //驼峰转下划线
    public static String camelToUnderline(String str) {
        Matcher matcher = Pattern.compile("[A-Z]").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, "_" + matcher.group(0).toLowerCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

    public static String underlineToCamel(String str) {
        Matcher matcher = Pattern.compile("_(\\w)").matcher(str);
        StringBuffer sb = new StringBuffer();
        while (matcher.find()) {
            matcher.appendReplacement(sb, matcher.group(1).toUpperCase());
        }
        matcher.appendTail(sb);
        return sb.toString();
    }

}
