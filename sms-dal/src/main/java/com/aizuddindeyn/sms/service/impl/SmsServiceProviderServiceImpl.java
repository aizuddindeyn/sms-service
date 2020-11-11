/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.service.impl;

import com.aizuddindeyn.sms.dao.SmsServiceProvider;
import com.aizuddindeyn.sms.mapper.SmsServiceProviderMapper;
import com.aizuddindeyn.sms.service.SmsServiceProviderService;
import com.aizuddindeyn.sms.util.LogLevelHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;

/**
 * @author aizuddindeyn
 * @date 11/7/2020
 */
@Service
public class SmsServiceProviderServiceImpl implements SmsServiceProviderService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsServiceProviderServiceImpl.class);

    private final SmsServiceProviderMapper smsServiceProviderMapper;

    @Autowired
    public SmsServiceProviderServiceImpl(SmsServiceProviderMapper smsServiceProviderMapper) {
        this.smsServiceProviderMapper = smsServiceProviderMapper;
    }

    /*@Cacheable(value = "SmsServiceProviders", key = "#root.methodName", unless = "#result == null")*/
    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public List<SmsServiceProvider> getSmsServiceProviders() {
        LogLevelHelper.logDebug(LOGGER, "Retrieving all SMS Service Providers");
        List<SmsServiceProvider> providers = smsServiceProviderMapper.getSmsServiceProviders();
        List<String> providersStr = new ArrayList<>();
        for (SmsServiceProvider sp : providers) {
            providersStr.add(sp.getServiceProvider().getCode());
        }
        LogLevelHelper.logDebug(LOGGER, "SMS Service Providers: {}", String.join(", ", providersStr));

        return providers;
    }
}
