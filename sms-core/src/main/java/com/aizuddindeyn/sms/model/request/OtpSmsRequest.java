/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.model.request;

import lombok.Data;

/**
 * @author aizuddindeyn
 * @date 11/11/2020
 */
@Data
public class OtpSmsRequest extends SmsRequest {

    private static final long serialVersionUID = 2251382167451687789L;

    private String otpId;

    private String previousSp;
}
