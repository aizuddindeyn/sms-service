/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.controller;

import com.aizuddindeyn.sms.common.AbstractController;
import com.aizuddindeyn.sms.facade.OtpSmsService;
import com.aizuddindeyn.sms.model.request.OtpSmsRequest;
import com.aizuddindeyn.sms.model.response.SmsResult;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;
import java.util.Map;

/**
 * @author aizuddindeyn
 * @date 11/11/2020
 */
@Controller
public class OtpSmsController extends AbstractController<OtpSmsRequest, SmsResult> {

    private static final Logger LOGGER = LoggerFactory.getLogger(OtpSmsController.class);

    private final OtpSmsService service;

    @Autowired
    public OtpSmsController(OtpSmsService service) {
        this.service = service;
    }

    @PostMapping(value = "/1.0/sms/otp")
    @ResponseBody
    @Override
    public Map<String, Object> send(OtpSmsRequest request, HttpServletRequest servletRequest) {
        return super.send(request, servletRequest);
    }

    @Override
    protected SmsResult process(OtpSmsRequest request) throws Exception {
        return service.send(request);
    }

    @Override
    protected Logger getLogger() {
        return LOGGER;
    }
}
