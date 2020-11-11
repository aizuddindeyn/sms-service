/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.facade;

import com.aizuddindeyn.sms.common.BaseRequest;
import com.aizuddindeyn.sms.common.BaseResult;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
public interface CoreSmsService {

    BaseResult sendSmsImpl(BaseRequest request, boolean otp) throws Exception;
}
