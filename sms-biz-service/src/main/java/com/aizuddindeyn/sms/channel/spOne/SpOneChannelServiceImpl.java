/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.channel.spOne;

import com.aizuddindeyn.sms.common.AbstractSmsCallable;
import com.aizuddindeyn.sms.common.BaseRequest;
import com.aizuddindeyn.sms.enums.ServiceProvider;
import com.aizuddindeyn.sms.facade.ChannelService;
import com.aizuddindeyn.sms.model.request.SmsRequest;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.aizuddindeyn.sms.util.Fields.DEBUG_ID;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
@Service
public class SpOneChannelServiceImpl implements ChannelService<SpOneParam> {

    @Value("${sms.sp1.endpoint}")
    private String endpoint;

    @Value("${sms.send.real}")
    private String sendRealSms;

    @Value("${sms.sp1.user}")
    private String user;

    @Value("${sms.sp1.pass}")
    private String pass;

    @Value("${sms.sp1.type}")
    private String type;

    @Value("${sms.sp1.from}")
    private String from;

    @Value("${sms.sp1.servid}")
    private String serviceId;

    @Override
    public SpOneParam buildParam(BaseRequest request, boolean otp) {
        SpOneParam smsParam = SpOneParam.builder()
                .user(user).pass(pass).type(type).from(from).serviceId(serviceId)
                .build();
        smsParam.setDebugId(MDC.get(DEBUG_ID));
        smsParam.setMobile(((SmsRequest) request).getMobile());
        smsParam.setMessage(((SmsRequest) request).getMessage());
        smsParam.setEndpoint(endpoint);
        smsParam.setSendRealSms(BooleanUtils.toBooleanDefaultIfNull
                (BooleanUtils.toBoolean(sendRealSms, "true", "false"), false));

        return smsParam;
    }

    @Override
    public AbstractSmsCallable<SpOneParam> buildCallable(SpOneParam smsParam) {
        return new SpOneCallable(smsParam);
    }

    @Override
    public ServiceProvider getChannel() {
        return ServiceProvider.SP1;
    }
}
