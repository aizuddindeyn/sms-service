/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.channel.alicloud;

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
public class AlicloudChannelServiceImpl implements ChannelService<AlicloudParam> {

    @Value("${sms.send.real}")
    private String sendRealSms;

    @Value("${sms.alicloud.accessId}")
    private String accessId;

    @Value("${sms.alicloud.secret}")
    private String secret;

    @Value("${sms.alicloud.region}")
    private String region;

    @Value("${sms.alicloud.domain}")
    private String domain;

    @Value("${sms.alicloud.version}")
    private String version;

    @Value("${sms.alicloud.action}")
    private String action;

    @Override
    public AlicloudParam buildParam(BaseRequest request, boolean otp) {
        AlicloudParam smsParam = AlicloudParam.builder()
                .accessId(accessId).secret(secret).region(region).domain(domain).version(version)
                .action(action).otp(otp)
                .build();
        smsParam.setDebugId(MDC.get(DEBUG_ID));
        smsParam.setMobile(((SmsRequest) request).getMobile());
        smsParam.setMessage(((SmsRequest) request).getMessage());
        smsParam.setSendRealSms(BooleanUtils.toBooleanDefaultIfNull
                (BooleanUtils.toBoolean(sendRealSms, "true", "false"), false));

        return smsParam;
    }

    @Override
    public AbstractSmsCallable<AlicloudParam> buildCallable(AlicloudParam smsParam) {
        return new AlicloudCallable(smsParam);
    }

    @Override
    public ServiceProvider getChannel() {
        return ServiceProvider.ALICLOUD;
    }
}
