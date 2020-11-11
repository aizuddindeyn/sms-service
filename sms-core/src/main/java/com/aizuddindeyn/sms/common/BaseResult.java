/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.common;

import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
@Getter
@Setter
public abstract class BaseResult implements Serializable {

    private static final long serialVersionUID = -3944319448325883667L;

    protected String statusCode;

    protected String data;
}
