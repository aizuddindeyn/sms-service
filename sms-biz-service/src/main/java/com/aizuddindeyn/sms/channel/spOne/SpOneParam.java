/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.channel.spOne;

import com.aizuddindeyn.sms.model.SmsParam;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
@Getter
@Setter
@Builder
public class SpOneParam extends SmsParam {

    private static final long serialVersionUID = -7296368260685001813L;

    private String user;

    private String pass;

    private String type;

    private String from;

    private String serviceId;
}
