/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.impl;

import com.aizuddindeyn.sms.common.AbstractSmsCallable;
import com.aizuddindeyn.sms.common.BaseResult;
import com.aizuddindeyn.sms.enums.ServiceProvider;
import com.aizuddindeyn.sms.exception.ServiceProviderException;
import com.aizuddindeyn.sms.facade.ChannelFactoryService;
import com.aizuddindeyn.sms.facade.ChannelService;
import com.aizuddindeyn.sms.model.SmsParam;
import com.aizuddindeyn.sms.model.ChannelRequest;
import com.aizuddindeyn.sms.util.LogLevelHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.InitializingBean;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.EnumMap;
import java.util.List;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/**
 * @author aizuddindeyn
 * @date 11/7/2020
 */
@Service
public class ChannelFactoryServiceImpl implements ChannelFactoryService, InitializingBean {

    private static final Logger LOGGER = LoggerFactory.getLogger(ChannelFactoryServiceImpl.class);

    private final EnumMap<ServiceProvider, ChannelService> serviceMap = new EnumMap<>(ServiceProvider.class);

    @Value("${sms.thread.pool:50}")
    private int threadSize;

    private ExecutorService executor;

    @Autowired
    public ChannelFactoryServiceImpl(List<ChannelService> channels) {
        for (ChannelService channel : channels) {
            serviceMap.put(channel.getChannel(), channel);
        }
    }

    @Override
    public void afterPropertiesSet() {
        executor = Executors.newFixedThreadPool(threadSize);
    }

    @Override
    public BaseResult sendThroughChannel(ChannelRequest request) throws Exception {
        LogLevelHelper.logDebug(LOGGER, "Executing channel factory with channel request {}",
                request.getServiceProvider().getCode());

        ChannelService<SmsParam> service = serviceMap.get(request.getServiceProvider());
        if (service == null) {
            throw new ServiceProviderException("SMS Service Provider " + request.getServiceProvider().getCode() +
                    " channel not exist", null);
        }

        SmsParam smsParam = service.buildParam(request.getSmsRequest(), request.isOtp());
        AbstractSmsCallable<SmsParam> callable = service.buildCallable(smsParam);

        Future<BaseResult> future = executor.submit(callable);
        return future.get();
    }
}
