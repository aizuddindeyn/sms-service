/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.facade;

import com.aizuddindeyn.sms.common.BaseResult;
import com.aizuddindeyn.sms.model.ChannelRequest;

/**
 * @author aizuddindeyn
 * @date 11/7/2020
 */
public interface ChannelFactoryService {

    BaseResult sendThroughChannel(ChannelRequest request) throws Exception;
}
