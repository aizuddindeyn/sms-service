/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.impl;

import com.aizuddindeyn.sms.common.AbstractSmsService;
import com.aizuddindeyn.sms.facade.CoreSmsService;
import com.aizuddindeyn.sms.facade.OtpSmsService;
import com.aizuddindeyn.sms.model.request.OtpSmsRequest;
import com.aizuddindeyn.sms.model.response.SmsResult;
import com.aizuddindeyn.sms.service.SmsLogService;
import com.aizuddindeyn.sms.util.LogLevelHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * @author aizuddindeyn
 * @date 11/11/2020
 */
@Service
public class OtpSmsServiceImpl extends AbstractSmsService implements OtpSmsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(OtpSmsServiceImpl.class);

    @Autowired
    public OtpSmsServiceImpl(CoreSmsService smsService, SmsLogService smsLogService) {
        super(smsService, smsLogService);
    }

    @Override
    public SmsResult send(OtpSmsRequest request) throws Exception {
        LogLevelHelper.logDebug(LOGGER, "Executing OtpSmsService.send");

        // Perform idempotence checking

        SmsResult smsResult = (SmsResult) coreSmsService.sendSmsImpl(request, true);

        saveSmsLog(smsResult, request);

        return smsResult;
    }
}
