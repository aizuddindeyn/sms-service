/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.service.impl;

import com.aizuddindeyn.sms.dao.SmsLog;
import com.aizuddindeyn.sms.repository.SmsLogRepository;
import com.aizuddindeyn.sms.service.SmsLogService;
import com.aizuddindeyn.sms.util.LogLevelHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
@Service
public class SmsLogServiceImpl implements SmsLogService {

    private static final Logger LOGGER = LoggerFactory.getLogger(SmsLogServiceImpl.class);

    private final SmsLogRepository repository;

    @Autowired
    public SmsLogServiceImpl(SmsLogRepository repository) {
        this.repository = repository;
    }

    @Transactional(readOnly = true, rollbackFor = Exception.class)
    @Override
    public SmsLog findIdempotence(SmsLog smsLog) {
        LogLevelHelper.logDebug(LOGGER, "Finding SmsLog by reference and mobile");
        return repository.findByReferenceAndMobile(smsLog);
    }

    @Transactional(readOnly = false, rollbackFor = Exception.class)
    @Override
    public void save(SmsLog smsLog) {
        LogLevelHelper.logDebug(LOGGER, "Inserting SmsLog: {}", smsLog);
        repository.save(smsLog);
    }
}
