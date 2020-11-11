/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.repository;

import com.aizuddindeyn.sms.dao.SmsLog;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

/**
 * @author aizuddindeyn
 * @date 11/6/2020
 */
@Repository
public interface SmsLogRepository extends CrudRepository<SmsLog, Long> {

    @Query("SELECT a FROM SmsLog a WHERE a.reference = :#{#smsLog.reference} AND a.mobile = :#{#smsLog.mobile}")
    SmsLog findByReferenceAndMobile(@Param("smsLog") SmsLog smsLog);
}
