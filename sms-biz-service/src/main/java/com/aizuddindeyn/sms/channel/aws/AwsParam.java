/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.channel.aws;

import com.aizuddindeyn.sms.model.SmsParam;
import com.amazonaws.services.sns.AmazonSNSClient;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author aizuddindeyn
 * @date 11/7/2020
 */
@Getter
@Setter
@Builder
public class AwsParam extends SmsParam {

    private static final long serialVersionUID = -7773975844948291176L;

    private AmazonSNSClient client;

    private String senderId;

    private boolean otp;
}
