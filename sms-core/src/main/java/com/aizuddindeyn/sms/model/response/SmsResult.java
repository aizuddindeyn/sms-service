/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.model.response;

import com.aizuddindeyn.sms.common.BaseResult;
import com.aizuddindeyn.sms.dao.SmsLog;
import com.aizuddindeyn.sms.enums.StatusCodeEnum;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
@Getter
@Setter
@Builder
public class SmsResult extends BaseResult {

    private static final long serialVersionUID = -4072294392409583865L;

    private String message;

    private String serviceProvider;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss[.SSS]", shape = JsonFormat.Shape.STRING)
    private LocalDateTime sentTime;

    public static SmsResult constructResult(SmsLog smsLog, boolean idempotent) {
        SmsResult smsResult =  SmsResult.builder()
                .message(StatusCodeEnum.findByCode(smsLog.getStatusCode()).getMessage())
                .serviceProvider(smsLog.getServiceProvider())
                .sentTime(smsLog.getSentTime())
                .build();
        smsResult.setStatusCode(smsLog.getStatusCode());
        smsResult.setData(idempotent ? "(Repeated) " + smsLog.getResponse() : smsLog.getResponse());

        return smsResult;
    }

    public static SmsResult constructResult(StatusCodeEnum statusCode, String message, String serviceProvider) {
        SmsResult smsResult = SmsResult.builder()
                .message(statusCode.getMessage())
                .serviceProvider(serviceProvider)
                .sentTime(StatusCodeEnum.SUCCESS == statusCode ? LocalDateTime.now() : null)
                .build();
        smsResult.setStatusCode(statusCode.getCode());
        smsResult.setData(message);

        return smsResult;
    }
}
