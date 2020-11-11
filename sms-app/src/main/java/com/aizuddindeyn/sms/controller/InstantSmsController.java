/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.controller;

import com.aizuddindeyn.sms.common.AbstractController;
import com.aizuddindeyn.sms.facade.InstantSmsService;
import com.aizuddindeyn.sms.model.request.SmsRequest;
import com.aizuddindeyn.sms.model.response.SmsResult;
import com.aizuddindeyn.sms.util.LogLevelHelper;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
@Controller
public class InstantSmsController extends AbstractController<SmsRequest, SmsResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(InstantSmsController.class);

    private final InstantSmsService instantSmsService;

    @Autowired
    public InstantSmsController(InstantSmsService instantSmsService) {
        this.instantSmsService = instantSmsService;
    }

    @PostMapping(value = "/1.0/sms/instant")
    @ResponseBody
    @Override
    public Map<String, Object> send(@RequestBody SmsRequest request, HttpServletRequest servletRequest) {
        return super.send(request, servletRequest);
    }

    @Override
    protected SmsResult process(SmsRequest request) throws Exception {
        return instantSmsService.send(request);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
