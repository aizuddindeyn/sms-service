/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.common;

import com.aizuddindeyn.sms.enums.ServiceProvider;
import com.aizuddindeyn.sms.enums.StatusCodeEnum;
import com.aizuddindeyn.sms.exception.ServiceProviderException;
import com.aizuddindeyn.sms.model.SmsParam;
import com.aizuddindeyn.sms.util.Fields;
import com.aizuddindeyn.sms.util.LogLevelHelper;
import org.apache.commons.lang3.time.StopWatch;
import org.slf4j.Logger;
import org.slf4j.MDC;

import java.util.concurrent.Callable;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
public abstract class AbstractSmsCallable<T extends SmsParam> implements Callable<BaseResult> {

    private static final String LOG_FORMAT = "{} SMS {} in {}{}, Status: {} {}, Mobile: {}, Message: {}, Response: {}";

    private static final String SKIP_MESSAGE = "Skipped real sending";

    protected final T smsParam;

    public AbstractSmsCallable(T smsParam) {
        this.smsParam = smsParam;
    }

    @Override
    public BaseResult call() throws Exception {
        MDC.put(Fields.DEBUG_ID, smsParam.getDebugId());
        LogLevelHelper.logDebug(getLogger(), "{} callable thread send sms to {}, content {}",
                getProvider().getCode(), smsParam.getMobile(), smsParam.getMessage());

        StopWatch stopWatch = new StopWatch();
        stopWatch.start();

        BaseResult result;
        try {
            if (smsParam.isSendRealSms()) {
                result = sendSms(smsParam);
            } else {
                result = constructResult(StatusCodeEnum.SUCCESS, SKIP_MESSAGE);
            }
            LogLevelHelper.logInfo(getLogger(), LOG_FORMAT, getProvider().getCode(), "Sent",
                    stopWatch.getTime(), "ms", StatusCodeEnum.SUCCESS.getCode(), StatusCodeEnum.SUCCESS.getMessage(),
                    smsParam.getMobile(), smsParam.getMessage(), result.getData());

        } catch (ServiceProviderException ex) {

            LogLevelHelper.logInfo(getLogger(), LOG_FORMAT, getProvider().getCode(), "Failed",
                    stopWatch.getTime(), "ms", ex.getStatusCode().getCode(), ex.getStatusCode().getMessage(),
                    smsParam.getMobile(), smsParam.getMessage(), ex.getMessage());
            result = constructResult(StatusCodeEnum.THIRD_PARTY_ERROR, ex.getMessage());

        } catch (RuntimeException ex) {

            LogLevelHelper.logInfo(getLogger(), LOG_FORMAT, getProvider().getCode(), "Failed",
                    stopWatch.getTime(), "ms", StatusCodeEnum.SYSTEM_ERROR.getCode(),
                    StatusCodeEnum.SYSTEM_ERROR.getMessage(), smsParam.getMobile(), smsParam.getMessage(),
                    ex.getMessage());
            result = constructResult(StatusCodeEnum.SYSTEM_ERROR, ex.getMessage());
        }

        MDC.clear();


        return result;
    }

    protected abstract BaseResult sendSms(T smsParam) throws ServiceProviderException;

    protected abstract BaseResult constructResult(StatusCodeEnum statusCode, String message);

    protected abstract ServiceProvider getProvider();

    protected abstract Logger getLogger();
}
