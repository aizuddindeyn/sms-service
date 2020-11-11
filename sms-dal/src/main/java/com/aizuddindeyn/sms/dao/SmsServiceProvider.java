/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.dao;

import com.aizuddindeyn.sms.enums.ServiceProvider;
import lombok.Builder;
import lombok.Data;

import javax.persistence.Column;
import javax.persistence.Entity;
import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import javax.persistence.Table;
import java.io.Serializable;
import java.time.LocalDateTime;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
@Entity
@Table(name = "sms_service_provider")
@Data
@Builder
public class SmsServiceProvider implements Serializable {

    private static final long serialVersionUID = 3683933476337326198L;

    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    @Column(name = "id")
    private Long id;

    @Enumerated(EnumType.STRING)
    @Column(name = "service_provider")
    private ServiceProvider serviceProvider;

    @Column(name = "weight")
    private int weight;

    @Column(name = "created_time")
    private LocalDateTime createdTime;

    @Column(name = "updated_time")
    private LocalDateTime updatedTime;
}
