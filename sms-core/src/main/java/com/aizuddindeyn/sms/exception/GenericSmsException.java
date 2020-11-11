/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.exception;

import com.aizuddindeyn.sms.enums.StatusCodeEnum;

/**
 * @author aizuddindeyn
 * @date 11/7/2020
 */
public class GenericSmsException extends Exception {

    public GenericSmsException(String message, Throwable t) {
        super(message, t);
    }

    public StatusCodeEnum getStatusCode() {
        return StatusCodeEnum.SYSTEM_ERROR;
    }
}
