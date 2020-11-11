/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.model.request;

import com.aizuddindeyn.sms.common.BaseRequest;
import com.fasterxml.jackson.annotation.JsonFormat;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
@Data
public class SmsRequest implements BaseRequest {

    private static final long serialVersionUID = 528219942689965737L;

    private String mobile;

    private String message;

    private String reference;

    @JsonFormat(pattern = "yyyy-MM-dd HH:mm:ss[.SSS]", shape = JsonFormat.Shape.STRING)
    private LocalDateTime requestTime;
}
