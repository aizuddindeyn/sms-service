/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.service;

import com.aizuddindeyn.sms.dao.SmsLog;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
public interface SmsLogService {

    SmsLog findIdempotence(SmsLog smsLog);

    void save(SmsLog smsLog);
}
