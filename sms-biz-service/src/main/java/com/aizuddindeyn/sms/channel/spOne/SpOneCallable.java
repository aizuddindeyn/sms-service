/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.channel.spOne;

import com.aizuddindeyn.sms.common.AbstractSmsCallable;
import com.aizuddindeyn.sms.common.BaseResult;
import com.aizuddindeyn.sms.enums.ServiceProvider;
import com.aizuddindeyn.sms.enums.StatusCodeEnum;
import com.aizuddindeyn.sms.exception.ServiceProviderException;
import com.aizuddindeyn.sms.model.response.SmsResult;
import com.aizuddindeyn.sms.util.LogLevelHelper;
import com.aizuddindeyn.sms.util.OkHttpUtil;
import com.aizuddindeyn.sms.util.StringUtil;
import com.google.common.collect.ImmutableMap;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.Map;
import java.util.Optional;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
public class SpOneCallable extends AbstractSmsCallable<SpOneParam> {

    private static final Logger LOGGER = LoggerFactory.getLogger(SpOneCallable.class);

    private static final String PARAM_USER = "user";
    private static final String PARAM_PASS = "pass";
    private static final String PARAM_TYPE = "type";
    private static final String PARAM_TO = "to";
    private static final String PARAM_FROM = "from";
    private static final String PARAM_TEXT = "text";
    private static final String PARAM_SERVICE_ID = "servid";

    private static final String RETURN_CODE = "Status";
    private static final String SUCCESS_CODE = "200";

    public SpOneCallable(SpOneParam smsParam) {
        super(smsParam);
    }

    @Override
    protected BaseResult sendSms(SpOneParam smsParam) throws ServiceProviderException {
        SmsResult result;
        try {
            smsParam.setMessage(StringUtil.replaceNonAsciiContent(smsParam.getMessage()));
            LogLevelHelper.logDebug(LOGGER, "Sending {} sms", getProvider().getCode());
            String responseStr =
                    OkHttpUtil.post(smsParam.getEndpoint(), composeParameters(smsParam), null, null);
            LogLevelHelper.logDebug(LOGGER, "{} sms response: {}", getProvider().getCode(), responseStr);

            if (isSuccess(responseStr)) {
                result = (SmsResult) constructResult(StatusCodeEnum.SUCCESS, responseStr);
            } else {
                result = (SmsResult) constructResult(StatusCodeEnum.THIRD_PARTY_ERROR, responseStr);
            }

        } catch (IOException ex) {
            throw new ServiceProviderException(ex.getMessage(), ex);
        } catch (JsonSyntaxException ex) {
            throw new ServiceProviderException("Invalid " + getProvider().getCode() + " response format", ex);
        }

        return result;
    }

    @Override
    protected BaseResult constructResult(StatusCodeEnum statusCode, String message) {
        return SmsResult.constructResult(statusCode, message, getProvider().getCode());
    }

    @Override
    protected ServiceProvider getProvider() {
        return ServiceProvider.SP1;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    private Map<String, String> composeParameters(SpOneParam smsParam) {
        ImmutableMap.Builder<String, String> mapBuilder = ImmutableMap.builder();
        return mapBuilder
                .put(PARAM_USER, smsParam.getUser())
                .put(PARAM_PASS, smsParam.getPass())
                .put(PARAM_TYPE, smsParam.getType())
                .put(PARAM_TO, smsParam.getMobile())
                .put(PARAM_FROM, smsParam.getFrom())
                .put(PARAM_TEXT, smsParam.getMessage())
                .put(PARAM_SERVICE_ID, smsParam.getServiceId())
                .build();
    }

    private String parseReturnCode(String responseString) {
        JsonObject respObject = JsonParser.parseString(responseString).getAsJsonObject();

        return Optional.ofNullable(respObject.get(RETURN_CODE).getAsString()).orElse("");
    }

    private boolean isSuccess(String responseString) {
        return StringUtils.isNotBlank(responseString) && SUCCESS_CODE.equals(parseReturnCode(responseString));
    }
}
