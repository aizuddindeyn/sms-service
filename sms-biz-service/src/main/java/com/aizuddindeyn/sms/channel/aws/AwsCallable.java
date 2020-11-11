/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.channel.aws;

import com.aizuddindeyn.sms.common.AbstractSmsCallable;
import com.aizuddindeyn.sms.common.BaseResult;
import com.aizuddindeyn.sms.enums.ServiceProvider;
import com.aizuddindeyn.sms.enums.StatusCodeEnum;
import com.aizuddindeyn.sms.exception.ServiceProviderException;
import com.aizuddindeyn.sms.model.response.SmsResult;
import com.aizuddindeyn.sms.util.LogLevelHelper;
import com.aizuddindeyn.sms.util.StringUtil;
import com.amazonaws.AmazonServiceException;
import com.amazonaws.services.sns.AmazonSNSClient;
import com.amazonaws.services.sns.model.MessageAttributeValue;
import com.amazonaws.services.sns.model.PublishRequest;
import com.amazonaws.services.sns.model.PublishResult;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.util.Assert;

import java.util.Map;

/**
 * @author aizuddindeyn
 * @date 11/7/2020
 */
public class AwsCallable extends AbstractSmsCallable<AwsParam> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AwsCallable.class);

    private static final String SMS_SENDER_FIELD = "AWS.SNS.SMS.SenderID";

    private static final String SMS_TYPE_FIELD = "AWS.SNS.SMS.SMSType";
    private static final String TRANSACTIONAL_VALUE = "Transactional";
    private static final String PROMOTIONAL_VALUE = "Promotional";

    public AwsCallable(AwsParam smsParam) {
        super(smsParam);
    }

    @Override
    protected BaseResult sendSms(AwsParam smsParam) throws ServiceProviderException {
        SmsResult result;
        try {
            smsParam.setMessage(StringUtil.replaceNonAsciiContent(smsParam.getMessage()));

            LogLevelHelper.logDebug(LOGGER, "Sending {} sms", getProvider().getCode());
            PublishResult response = sendAwsSms(smsParam);
            LogLevelHelper.logDebug(LOGGER, "{} sms response: {}", getProvider().getCode(), response);

            if (isSuccess(response)) {
                result = (SmsResult) constructResult(StatusCodeEnum.SUCCESS, response.toString());
            } else {
                String responseStr = response != null ? response.toString() : "No response";
                result = (SmsResult) constructResult(StatusCodeEnum.THIRD_PARTY_ERROR, responseStr);
            }

        } catch (AmazonServiceException ex) {
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
        return ServiceProvider.AWS;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    private PublishResult sendAwsSms(AwsParam smsParam) {
        AmazonSNSClient client = smsParam.getClient();
        Assert.notNull(client, "Amazon SNS Client must be provided");

        return client.publish(
                new PublishRequest()
                        .withMessage(smsParam.getMessage())
                        .withPhoneNumber(smsParam.getMobile())
                        .withMessageAttributes(constructAttributes(smsParam)));
    }

    private Map<String, MessageAttributeValue> constructAttributes(AwsParam smsParam) {
        String type = smsParam.isOtp() ? TRANSACTIONAL_VALUE : PROMOTIONAL_VALUE;
        ImmutableMap.Builder<String, MessageAttributeValue> builder = new ImmutableMap.Builder<>();
        return builder
                .put(SMS_SENDER_FIELD,
                        new MessageAttributeValue().withStringValue(smsParam.getSenderId()).withDataType("String"))
                .put(SMS_TYPE_FIELD,
                        new MessageAttributeValue().withStringValue(type).withDataType("String"))
                .build();
    }

    private boolean isSuccess(PublishResult response) {
        return response != null && StringUtils.isNotBlank(response.getMessageId());
    }
}
