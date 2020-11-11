/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.util;

import java.text.Normalizer;
import java.util.regex.Pattern;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
public class StringUtil {

    private static final Pattern NON_ASCII_PATTERN = Pattern.compile("[^\\p{ASCII}]");

    private StringUtil() {
        throw new IllegalStateException("Util class");
    }

    public static String replaceNonAsciiContent(String content) {
        String normalizedContent = Normalizer.normalize(content, Normalizer.Form.NFKD);
        return NON_ASCII_PATTERN.matcher(normalizedContent).replaceAll("");
    }
}
