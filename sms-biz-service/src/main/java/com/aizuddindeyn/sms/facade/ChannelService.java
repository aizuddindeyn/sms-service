/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.facade;

import com.aizuddindeyn.sms.common.AbstractSmsCallable;
import com.aizuddindeyn.sms.common.BaseRequest;
import com.aizuddindeyn.sms.enums.ServiceProvider;
import com.aizuddindeyn.sms.model.SmsParam;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
public interface ChannelService<T extends SmsParam> {

    T buildParam(BaseRequest request, boolean otp);

    AbstractSmsCallable<T> buildCallable(T smsParam);

    ServiceProvider getChannel();
}
