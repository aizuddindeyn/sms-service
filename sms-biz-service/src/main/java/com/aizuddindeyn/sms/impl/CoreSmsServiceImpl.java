/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.impl;

import com.aizuddindeyn.sms.common.BaseRequest;
import com.aizuddindeyn.sms.common.BaseResult;
import com.aizuddindeyn.sms.dao.SmsServiceProvider;
import com.aizuddindeyn.sms.enums.ServiceProvider;
import com.aizuddindeyn.sms.enums.StatusCodeEnum;
import com.aizuddindeyn.sms.exception.GenericSmsException;
import com.aizuddindeyn.sms.facade.ChannelFactoryService;
import com.aizuddindeyn.sms.facade.CoreSmsService;
import com.aizuddindeyn.sms.model.ChannelRequest;
import com.aizuddindeyn.sms.service.SmsServiceProviderService;
import com.aizuddindeyn.sms.util.LogLevelHelper;
import lombok.Builder;
import lombok.Getter;
import org.apache.commons.collections4.CollectionUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.Assert;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;
import java.util.stream.Collectors;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
@Service
public class CoreSmsServiceImpl implements CoreSmsService {

    private static final Logger LOGGER = LoggerFactory.getLogger(CoreSmsServiceImpl.class);

    private final ChannelFactoryService channelFactoryService;

    private final SmsServiceProviderService smsServiceProviderService;

    @Autowired
    public CoreSmsServiceImpl(ChannelFactoryService channelFactoryService,
                              SmsServiceProviderService smsServiceProviderService) {
        this.channelFactoryService = channelFactoryService;
        this.smsServiceProviderService = smsServiceProviderService;
    }

    @Override
    public BaseResult sendSmsImpl(BaseRequest request, boolean otp) throws Exception {
        LogLevelHelper.logDebug(LOGGER, "Executing CoreSmsService.sendSmsImpl");

        List<SmsServiceProvider> smsServiceProviders = smsServiceProviderService.getSmsServiceProviders();
        if (CollectionUtils.isEmpty(smsServiceProviders)) {
            LogLevelHelper.logError(LOGGER, "No SMS Service Providers available");
            throw new GenericSmsException("No SMS Service Providers available", null);
        }

        int tryCount = 1;
        BaseResult result = null;
        while (!CollectionUtils.isEmpty(smsServiceProviders)) {
            ServiceProvider serviceProvider;
            if (smsServiceProviders.size() == 1) {
                serviceProvider = smsServiceProviders.get(0).getServiceProvider();
            } else {
                serviceProvider =
                        Optional.ofNullable(randomProvider(smsServiceProviders))
                                .orElse(SpBean.builder().build())
                                .getServiceProvider();
            }
            Assert.notNull(serviceProvider, "SMS Service Provider must be provided");
            LogLevelHelper.logDebug(LOGGER, "Selected SMS Service Provider: {}, try count: {}",
                    serviceProvider.getCode(), tryCount);

            ChannelRequest channelRequest = ChannelRequest.builder()
                    .serviceProvider(serviceProvider).smsRequest(request).otp(otp).build();

            result = channelFactoryService.sendThroughChannel(channelRequest);
            if (StatusCodeEnum.SUCCESS.getCode().equals(result.getStatusCode())) {
                break;
            }

            smsServiceProviders = smsServiceProviders.stream()
                            .filter(s -> serviceProvider != (s.getServiceProvider()))
                            .collect(Collectors.toList());
            tryCount++;
        }

        Assert.notNull(result, "Failed to send on all SMS Service Providers");

        return result;
    }

    private SpBean randomProvider(List<SmsServiceProvider> serviceProviders) {
        int totalWeight = serviceProviders.stream().mapToInt(SmsServiceProvider::getWeight).sum();
        List<SpBean> spBeans = convertBean(serviceProviders, totalWeight);

        LogLevelHelper.logDebug(LOGGER, "Randomizing SMS Service Providers based on weight");
        int section = ThreadLocalRandom.current().nextInt(totalWeight);
        SpBean sp = null;
        for (SpBean s : spBeans) {
            if (section >= s.getMinSection() && section < s.getMaxSection()) {
                sp = s;
                break;
            }
        }

        return sp;
    }

    private List<SpBean> convertBean(List<SmsServiceProvider> serviceProviders, int totalWeight) {
        List<SpBean> spBeans = new ArrayList<>();
        int section = 0;
        for (SmsServiceProvider sp : serviceProviders) {
            if (sp.getWeight() > 0) {
                spBeans.add(SpBean.builder().serviceProvider(sp.getServiceProvider()).totalWeight(totalWeight)
                        .minSection(section).maxSection(section + sp.getWeight())
                        .build());
                section += sp.getWeight();
            }
        }

        return spBeans;
    }

    @Getter
    @Builder
    private static class SpBean {
        private final ServiceProvider serviceProvider;

        private final int totalWeight;

        private final int minSection;

        private final int maxSection;
    }
}
