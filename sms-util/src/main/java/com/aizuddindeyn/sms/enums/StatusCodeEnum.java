/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.enums;

import com.google.common.collect.ImmutableMap;

import java.util.Map;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
public enum StatusCodeEnum {

    /**
     * Status Code Enum
     */
    SUCCESS("00", "Success"),

    MISSING_PARAMETER("76", "Invalid Parameter:[${}]"),

    INVALID_REQUEST("79", "Invalid Request:[${}]"),

    SYSTEM_ERROR("A0", "Unexpected Error"),

    THIRD_PARTY_ERROR("A1", "Third Party Call Error")
    ;

    private final String code;

    private final String message;

    private static final Map<String, StatusCodeEnum> enumMap;

    static {
        ImmutableMap.Builder<String, StatusCodeEnum> builder = ImmutableMap.builder();
        for (StatusCodeEnum e : StatusCodeEnum.values()) {
            builder.put(e.code, e);
        }
        enumMap = builder.build();
    }

    StatusCodeEnum(String code, String message) {
        this.code = code;
        this.message = message;
    }

    public static StatusCodeEnum findByCode(String code) {
        if (code == null) {
            return null;
        }
        return enumMap.get(code);
    }

    public String getCode() {
        return this.code;
    }

    public String getMessage() {
        return this.message;
    }
}
