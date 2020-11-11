/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.common;

import com.aizuddindeyn.sms.dao.SmsLog;
import com.aizuddindeyn.sms.enums.SmsRequestType;
import com.aizuddindeyn.sms.enums.StatusCodeEnum;
import com.aizuddindeyn.sms.facade.CoreSmsService;
import com.aizuddindeyn.sms.model.request.OtpSmsRequest;
import com.aizuddindeyn.sms.model.request.SmsRequest;
import com.aizuddindeyn.sms.model.response.SmsResult;
import com.aizuddindeyn.sms.service.SmsLogService;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
public abstract class AbstractSmsService {

    protected final SmsLogService smsLogService;

    protected final CoreSmsService coreSmsService;

    public AbstractSmsService(CoreSmsService coreSmsService, SmsLogService smsLogService) {
        this.coreSmsService = coreSmsService;
        this.smsLogService = smsLogService;
    }

    protected SmsLog checkIdempotence(SmsRequest request) {
        SmsLog smsLogRequest = SmsLog.builder().reference(request.getReference()).mobile(request.getMobile())
                .build();
        return smsLogService.findIdempotence(smsLogRequest);
    }

    protected boolean isIdempotent(SmsLog smsLog) {
        return smsLog != null;
    }

    protected void saveSmsLog(SmsResult result, SmsRequest request) {
        SmsLog smsLog = SmsLog.builder().reference(request.getReference()).mobile(request.getMobile())
                .message(request.getMessage()).response(result.getData())
                .sentTime(result.getSentTime()).requestTime(request.getRequestTime())
                .error(!StatusCodeEnum.SUCCESS.getCode().equals(result.getStatusCode()))
                .statusCode(result.getStatusCode()).serviceProvider(result.getServiceProvider())
                .requestType(SmsRequestType.SMS)
                .build();
        smsLogService.save(smsLog);
    }

    protected void saveSmsLog(SmsResult result, OtpSmsRequest request) {
        SmsLog smsLog = SmsLog.builder().reference(request.getReference()).mobile(request.getMobile())
                .message(request.getMessage()).response(result.getData())
                .sentTime(result.getSentTime()).requestTime(request.getRequestTime())
                .error(!StatusCodeEnum.SUCCESS.getCode().equals(result.getStatusCode()))
                .statusCode(result.getStatusCode()).serviceProvider(result.getServiceProvider())
                .requestType(SmsRequestType.OTP).requestReference(request.getOtpId())
                .build();
        smsLogService.save(smsLog);
    }
}
