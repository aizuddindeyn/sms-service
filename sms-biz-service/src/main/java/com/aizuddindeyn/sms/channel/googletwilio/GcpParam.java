/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.channel.googletwilio;

import com.aizuddindeyn.sms.model.SmsParam;
import com.twilio.http.TwilioRestClient;
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
public class GcpParam extends SmsParam {

    private static final long serialVersionUID = 254124045789586123L;

    private TwilioRestClient client;

    private String accountSid;

    private String accountToken;

    private String from;
}
