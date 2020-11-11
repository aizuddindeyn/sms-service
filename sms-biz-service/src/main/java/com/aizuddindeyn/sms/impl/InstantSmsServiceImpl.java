/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.impl;

import com.aizuddindeyn.sms.common.AbstractSmsService;
import com.aizuddindeyn.sms.dao.SmsLog;
import com.aizuddindeyn.sms.enums.SmsRequestType;
import com.aizuddindeyn.sms.enums.StatusCodeEnum;
import com.aizuddindeyn.sms.facade.CoreSmsService;
import com.aizuddindeyn.sms.facade.InstantSmsService;
import com.aizuddindeyn.sms.model.request.SmsRequest;
import com.aizuddindeyn.sms.model.response.SmsResult;
import com.aizuddindeyn.sms.service.SmsLogService;
import com.aizuddindeyn.sms.util.LogLevelHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
@Service
public class InstantSmsServiceImpl extends AbstractSmsService implements InstantSmsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstantSmsServiceImpl.class);

    @Autowired
    public InstantSmsServiceImpl(CoreSmsService coreSmsService, SmsLogService smsLogService) {
        super(coreSmsService, smsLogService);
    }

    @Override
    public SmsResult send(SmsRequest request) throws Exception {
        LogLevelHelper.logDebug(LOGGER, "Executing InstantSmsService.send");

        LogLevelHelper.logDebug(LOGGER, "Checking idempotence SmsRequest, mobile: {}, reference: {}",
                request.getMobile(), request.getReference());
        SmsLog existingLog = checkIdempotence(request);

        if (isIdempotent(existingLog)) {
            LogLevelHelper.logDebug(LOGGER, "Existing SmsRequest found, mobile: {}, reference: {}",
                    request.getMobile(), request.getReference());
            return SmsResult.constructResult(existingLog, true);
        }

        SmsResult smsResult = (SmsResult) coreSmsService.sendSmsImpl(request, false);

        saveSmsLog(smsResult, request);

        return smsResult;
    }
}
