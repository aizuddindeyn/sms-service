/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.facade;

import com.aizuddindeyn.sms.model.request.SmsRequest;
import com.aizuddindeyn.sms.model.response.SmsResult;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
public interface InstantSmsService {

    SmsResult send(SmsRequest request) throws Exception;
}
