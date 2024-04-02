package com.study.string_unit;

public class StringUtils {

    public static String concatenate(String str1, String str2) {
        return str1 + str2;
    }

    public static boolean isPalindrome(String str) {
        return str.equals(new StringBuilder(str).reverse().toString());
    }

    public static String removeDuplicates(String str) {
        StringBuilder result = new StringBuilder();
        for (char ch : str.toCharArray()) {
            if (result.indexOf(String.valueOf(ch)) == -1) {
                result.append(ch);
            }
        }
        return result.toString();
    }

}
