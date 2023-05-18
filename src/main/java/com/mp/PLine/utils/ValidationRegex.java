package com.mp.PLine.utils;

import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ValidationRegex {
    // 이메일 형식 체크
    public static boolean isRegexEmail(String email) {
        String regex = "^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\.[A-Z]{2,6}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(email);

        return matcher.find();
    }

    public static boolean isRegexPhone(String phone) {
        String regex = "^\\d{3}-\\d{3,4}-\\d{4}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(phone);

        return matcher.find();
    }

    public static boolean isRegexBirth(String birth) {
        String regex = "^\\d{4}\\.\\d{2}\\.\\d{2}$";
        Pattern pattern = Pattern.compile(regex, Pattern.CASE_INSENSITIVE);
        Matcher matcher = pattern.matcher(birth);

        return matcher.find();
    }
}

