/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.service;

import com.aizuddindeyn.sms.dao.SmsServiceProvider;

import java.util.List;

/**
 * @author aizuddindeyn
 * @date 11/7/2020
 */
public interface SmsServiceProviderService {

    List<SmsServiceProvider> getSmsServiceProviders();
}
