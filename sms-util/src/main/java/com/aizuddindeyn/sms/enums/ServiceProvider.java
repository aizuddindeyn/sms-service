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
public enum ServiceProvider {

    /**
     * Service Provider Enum
     */
    SP1("SP1"),

    ALICLOUD("Alicloud SMS"),

    GCP("Google Cloud Twilio"),

    AWS("Amazon SNS")
    ;

    private final String code;

    private static final Map<String, ServiceProvider> enumMap;

    static {
        ImmutableMap.Builder<String, ServiceProvider> builder = ImmutableMap.builder();
        for (ServiceProvider e : ServiceProvider.values()) {
            builder.put(e.code, e);
        }
        enumMap = builder.build();
    }

    ServiceProvider(String code) {
        this.code = code;
    }

    public static ServiceProvider findByCode(String code) {
        if (code == null) {
            return null;
        }
        return enumMap.get(code);
    }

    public String getCode() {
        return this.code;
    }
}
