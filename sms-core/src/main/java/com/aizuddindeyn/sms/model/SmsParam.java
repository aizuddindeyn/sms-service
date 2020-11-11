/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.model;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
@Getter
@Setter
public class SmsParam implements Serializable {

    private static final long serialVersionUID = 8287769304346085746L;

    protected String debugId;

    protected String mobile;

    protected String message;

    protected String endpoint;

    protected boolean sendRealSms;
}
