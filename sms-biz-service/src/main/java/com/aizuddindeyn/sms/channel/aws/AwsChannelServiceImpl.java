/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.channel.aws;

import com.aizuddindeyn.sms.common.AbstractSmsCallable;
import com.aizuddindeyn.sms.common.BaseRequest;
import com.aizuddindeyn.sms.enums.ServiceProvider;
import com.aizuddindeyn.sms.facade.ChannelService;
import com.aizuddindeyn.sms.model.request.SmsRequest;
import com.amazonaws.auth.InstanceProfileCredentialsProvider;
import com.amazonaws.services.sns.AmazonSNSClient;
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
public class AwsChannelServiceImpl implements ChannelService<AwsParam> {

    private static final InstanceProfileCredentialsProvider credentials =
            new InstanceProfileCredentialsProvider(true);

    @Value("${sms.send.real}")
    private String sendRealSms;

    @Value("${sms.aws.senderId}")
    private String senderId;

    @Override
    public AwsParam buildParam(BaseRequest request, boolean otp) {
        AwsParam smsParam = AwsParam.builder()
                .senderId(senderId).otp(otp)
                .build();
        smsParam.setDebugId(MDC.get(DEBUG_ID));
        smsParam.setMobile(((SmsRequest) request).getMobile());
        smsParam.setMessage(((SmsRequest) request).getMessage());
        smsParam.setSendRealSms(BooleanUtils.toBooleanDefaultIfNull
                (BooleanUtils.toBoolean(sendRealSms, "true", "false"), false));

        AmazonSNSClient client;
        if (smsParam.isSendRealSms()) {
            client = (AmazonSNSClient) AmazonSNSClient.builder().withCredentials(credentials).build();
        } else {
            client = new AwsFakeClient();
        }
        smsParam.setClient(client);

        return smsParam;
    }

    @Override
    public AbstractSmsCallable<AwsParam> buildCallable(AwsParam smsParam) {
        return new AwsCallable(smsParam);
    }

    @Override
    public ServiceProvider getChannel() {
        return ServiceProvider.AWS;
    }
}
