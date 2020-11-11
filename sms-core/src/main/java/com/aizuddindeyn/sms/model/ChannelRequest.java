/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.model;

import com.aizuddindeyn.sms.common.BaseRequest;
import com.aizuddindeyn.sms.enums.ServiceProvider;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author aizuddindeyn
 * @date 11/7/2020
 */
@Getter
@Setter
@Builder
public class ChannelRequest implements Serializable {

    private static final long serialVersionUID = 2564347616044154761L;

    private BaseRequest smsRequest;

    private ServiceProvider serviceProvider;

    private boolean otp;
}
