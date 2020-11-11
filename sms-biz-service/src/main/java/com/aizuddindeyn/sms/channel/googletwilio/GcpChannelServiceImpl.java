/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.channel.googletwilio;

import com.aizuddindeyn.sms.common.AbstractSmsCallable;
import com.aizuddindeyn.sms.common.BaseRequest;
import com.aizuddindeyn.sms.enums.ServiceProvider;
import com.aizuddindeyn.sms.facade.ChannelService;
import com.aizuddindeyn.sms.model.request.SmsRequest;
import com.twilio.Twilio;
import org.apache.commons.lang3.BooleanUtils;
import org.slf4j.MDC;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import static com.aizuddindeyn.sms.util.Fields.DEBUG_ID;

/**
 * @author aizuddindeyn
 * @date 11/7/2020
 */
@Service
public class GcpChannelServiceImpl implements ChannelService<GcpParam> {

    @Value("${sms.send.real}")
    private String sendRealSms;

    @Value("${sms.gcp.twilio.sid}")
    private String accountSid;

    @Value("${sms.gcp.twilio.token}")
    private String accountToken;

    @Value("${sms.gcp.twilio.from}")
    private String from;

    @Override
    public GcpParam buildParam(BaseRequest request, boolean otp) {
        Twilio.init(accountSid, accountToken);
        GcpParam smsParam = GcpParam.builder().client(Twilio.getRestClient()).from(from)
                .build();
        smsParam.setDebugId(MDC.get(DEBUG_ID));
        smsParam.setMobile(((SmsRequest) request).getMobile());
        smsParam.setMessage(((SmsRequest) request).getMessage());
        smsParam.setSendRealSms(BooleanUtils.toBooleanDefaultIfNull
                (BooleanUtils.toBoolean(sendRealSms, "true", "false"), false));

        return smsParam;
    }

    @Override
    public AbstractSmsCallable<GcpParam> buildCallable(GcpParam smsParam) {
        return new GcpCallable(smsParam);
    }

    @Override
    public ServiceProvider getChannel() {
        return ServiceProvider.GCP;
    }
}
