/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.exception;

import com.aizuddindeyn.sms.enums.StatusCodeEnum;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
public class ServiceProviderException extends RuntimeException {

    public ServiceProviderException(String message, Throwable t) {
        super(message, t);
    }

    public StatusCodeEnum getStatusCode() {
        return StatusCodeEnum.THIRD_PARTY_ERROR;
    }
}
