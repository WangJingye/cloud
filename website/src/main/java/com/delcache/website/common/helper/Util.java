package com.delcache.website.common.helper;

public class Util {

    public static String trim(String str, String element) {
        int elementLength = element.length();
        boolean beginIndexFlag = (str.indexOf(element) == 0);
        boolean endIndexFlag = (str.lastIndexOf(element) + elementLength == str.length());
        while (beginIndexFlag || endIndexFlag) {
            int start = str.indexOf(element);
            if (start == 0) {
                str = str.substring(elementLength);
            }
            int end = str.lastIndexOf(element);

            if (end != -1 && end + elementLength == str.length()) {
                str = str.substring(0, end);
            }
            beginIndexFlag = (str.indexOf(element) == 0);
            endIndexFlag = (str.lastIndexOf(element) != -1 && str.lastIndexOf(element) + elementLength == str.length());
        }
        return str;
    }
}
