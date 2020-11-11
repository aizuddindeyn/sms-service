/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.facade;

import com.aizuddindeyn.sms.model.request.OtpSmsRequest;
import com.aizuddindeyn.sms.model.response.SmsResult;

/**
 * @author aizuddindeyn
 * @date 11/11/2020
 */
public interface OtpSmsService {

    SmsResult send(OtpSmsRequest request) throws Exception;
}
