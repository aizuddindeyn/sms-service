/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.channel.alicloud;

import com.aizuddindeyn.sms.common.AbstractSmsCallable;
import com.aizuddindeyn.sms.common.BaseResult;
import com.aizuddindeyn.sms.enums.ServiceProvider;
import com.aizuddindeyn.sms.enums.StatusCodeEnum;
import com.aizuddindeyn.sms.exception.ServiceProviderException;
import com.aizuddindeyn.sms.model.response.SmsResult;
import com.aizuddindeyn.sms.util.LogLevelHelper;
import com.aizuddindeyn.sms.util.StringUtil;
import com.aliyuncs.CommonRequest;
import com.aliyuncs.CommonResponse;
import com.aliyuncs.DefaultAcsClient;
import com.aliyuncs.IAcsClient;
import com.aliyuncs.exceptions.ClientException;
import com.aliyuncs.http.MethodType;
import com.aliyuncs.profile.DefaultProfile;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.google.gson.JsonSyntaxException;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.HttpStatus;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
public class AlicloudCallable extends AbstractSmsCallable<AlicloudParam> {

    private static final Logger LOGGER = LoggerFactory.getLogger(AlicloudCallable.class);

    private static final String PARAM_TO = "To";
    private static final String PARAM_MESSAGE = "Message";
    private static final String PARAM_TYPE = "Type";
    private static final String OTP_VALUE = "OTP";

    private static final String RESPONSE_CODE = "ResponseCode";
    private static final String RESPONSE_OK = "OK";

    public AlicloudCallable(AlicloudParam smsParam) {
        super(smsParam);
    }

    @Override
    protected BaseResult sendSms(AlicloudParam smsParam) throws ServiceProviderException {
        SmsResult result;
        try {
            smsParam.setMessage(StringUtil.replaceNonAsciiContent(smsParam.getMessage()));
            LogLevelHelper.logDebug(LOGGER, "Sending {} sms", getProvider().getCode());
            CommonResponse response = sendAlicloudSms(smsParam);

            if (isSuccess(response)) {
                LogLevelHelper.logDebug(LOGGER, "{} sms response: {}", getProvider().getCode(),
                        response.getData());
                result = (SmsResult) constructResult(StatusCodeEnum.SUCCESS, response.getData());
            } else {
                result = (SmsResult) constructResult(StatusCodeEnum.THIRD_PARTY_ERROR, response.getData());
            }

        } catch (ClientException ex) {
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
        return ServiceProvider.ALICLOUD;
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }

    private CommonResponse sendAlicloudSms(AlicloudParam smsParam) throws ClientException {
        DefaultProfile profile = DefaultProfile.getProfile(smsParam.getRegion(), smsParam.getAccessId(),
                smsParam.getSecret());

        IAcsClient client = new DefaultAcsClient(profile);
        CommonRequest request = new CommonRequest();

        request.setMethod(MethodType.POST);
        request.setDomain(smsParam.getDomain());
        request.setVersion(smsParam.getVersion());
        request.setAction(smsParam.getAction());
        request.putQueryParameter(PARAM_TO, smsParam.getMobile());
        request.putQueryParameter(PARAM_MESSAGE, smsParam.getMessage());
        if (smsParam.getOtp()) {
            request.putQueryParameter(PARAM_TYPE, OTP_VALUE);
        }

        return client.getCommonResponse(request);
    }

    private boolean isSuccess(CommonResponse response) {
        JsonObject responseObj = null;
        if (StringUtils.isNotBlank(response.getData())) {
            responseObj = JsonParser.parseString(response.getData()).getAsJsonObject();
        }

        return responseObj != null && parseSuccessResponse(response, responseObj);
    }

    private boolean parseSuccessResponse(CommonResponse response, JsonObject responseObj) {
        return HttpStatus.SC_OK == response.getHttpStatus() && responseObj.get(RESPONSE_CODE) != null &&
                RESPONSE_OK.equals(responseObj.get(RESPONSE_CODE).toString());
    }
}
