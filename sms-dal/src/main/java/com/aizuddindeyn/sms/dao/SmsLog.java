/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.dao;

import com.aizuddindeyn.sms.enums.SmsRequestType;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.FetchType;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Index;
import javax.persistence.ManyToOne;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
@Entity
@Table(name = "sms_log", indexes = {
        @Index(name = "idx_reference", columnList = "reference"),
        @Index(name = "idx_mobile", columnList = "mobile"),
        @Index(name = "idx_sent_time", columnList = "sent_time"),
        @Index(name = "idx_service_provider", columnList = "service_provider")
})
@Data
@Builder
public class SmsLog implements Serializable {

    private static final long serialVersionUID = -1876044809064979227L;

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "id")
    private Long id;

    @Column(name = "reference")
    private String reference;

    @Column(name = "mobile")
    private String mobile;

    @Column(name = "message")
    private String message;

    @Column(name = "sent_time")
    private LocalDateTime sentTime;

    @Column(name = "request_time")
    private LocalDateTime requestTime;

    @Column(name = "response")
    private String response;

    @Column(name = "error")
    private Boolean error;

    @Column(name = "status_code")
    private String statusCode;

    @Column(name = "service_provider")
    private String serviceProvider;

    @Enumerated(EnumType.STRING)
    @Column(name = "request_type")
    private SmsRequestType requestType;

    @Column(name = "request_reference")
    private String requestReference;
}
