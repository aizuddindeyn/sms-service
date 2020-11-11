/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.channel.alicloud;

import com.aizuddindeyn.sms.model.SmsParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
@Getter
@Setter
@Builder
public class AlicloudParam extends SmsParam {

    private static final long serialVersionUID = 3142867322160770530L;

    private String accessId;

    private String secret;

    private String region;

    private String domain;

    private String version;

    private String action;

    private Boolean otp;
}
