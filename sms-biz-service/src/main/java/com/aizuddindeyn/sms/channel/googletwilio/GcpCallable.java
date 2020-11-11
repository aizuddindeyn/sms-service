/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.channel.googletwilio;

import com.aizuddindeyn.sms.common.AbstractSmsCallable;
import com.aizuddindeyn.sms.common.BaseResult;
import com.aizuddindeyn.sms.enums.ServiceProvider;
import com.aizuddindeyn.sms.enums.StatusCodeEnum;
import com.aizuddindeyn.sms.exception.ServiceProviderException;
import com.aizuddindeyn.sms.model.response.SmsResult;
import com.aizuddindeyn.sms.util.LogLevelHelper;
import com.aizuddindeyn.sms.util.StringUtil;
import com.twilio.exception.ApiException;
import com.twilio.rest.api.v2010.account.Message;
import com.twilio.type.PhoneNumber;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author aizuddindeyn
 * @date 11/7/2020
 */
public class GcpCallable extends AbstractSmsCallable<GcpParam> {

    private static final Logger LOGGER = LoggerFactory.getLogger(GcpCallable.class);

    public GcpCallable(GcpParam smsParam) {
        super(smsParam);
    }

    @Override
    protected BaseResult sendSms(GcpParam smsParam) throws ServiceProviderException {
        SmsResult result;
        try {
            smsParam.setMessage(StringUtil.replaceNonAsciiContent(smsParam.getMessage()));

            LogLevelHelper.logDebug(LOGGER, "Sending {} sms", getProvider().getCode());
            Message response = sendTwilioSms(smsParam);
            LogLevelHelper.logDebug(LOGGER, "{} sms response: {}", getProvider().getCode(), response);

            if (isSuccess(response)) {
                result = (SmsResult) constructResult(StatusCodeEnum.SUCCESS, response.toString());
            } else {
                String responseStr = response != null ? response.toString() : "No response";
                result = (SmsResult) constructResult(StatusCodeEnum.THIRD_PARTY_ERROR, responseStr);
            }

        } catch (ApiException ex) {
            throw new ServiceProviderException(ex.getMessage(), ex);
        }

        return result;
    }

    @Override
    protected BaseResult constructResult(StatusCodeEnum statusCode, String message) {
        return SmsResult.constructResult(statusCode, message, getProvider().getCode());
    }

    @Override
    protected ServiceProvider getProvider() {
        return ServiceProvider.GCP;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    private Message sendTwilioSms(GcpParam smsParam) {
        return Message.creator(
                new PhoneNumber(smsParam.getMobile()),
                new PhoneNumber(smsParam.getFrom()),
                smsParam.getMessage())
                .create(smsParam.getClient());
    }

    private boolean isSuccess(Message response) {
        return response != null && Message.Status.FAILED != response.getStatus();
    }
}
