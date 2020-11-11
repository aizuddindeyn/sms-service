/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.mapper;

import com.aizuddindeyn.sms.dao.SmsServiceProvider;
import com.aizuddindeyn.sms.enums.ServiceProvider;
import org.apache.ibatis.annotations.Result;
import org.apache.ibatis.annotations.Results;
import org.apache.ibatis.annotations.Select;
import org.apache.ibatis.type.EnumTypeHandler;
import org.apache.ibatis.type.JdbcType;
import org.apache.ibatis.type.LocalDateTimeTypeHandler;

import java.util.List;

/**
 * @author aizuddindeyn
 * @date 11/7/2020
 */
public interface SmsServiceProviderMapper {

    @Results(id = "smsServiceProviderResultMap", value = {
            @Result(property = "id", column = "id", jdbcType = JdbcType.BIGINT),
            @Result(property = "serviceProvider", column = "service_provider", jdbcType = JdbcType.VARCHAR,
                    typeHandler = EnumTypeHandler.class),
            @Result(property = "weight", column = "weight", jdbcType = JdbcType.INTEGER),
            @Result(property = "createdTime", column = "created_time", jdbcType = JdbcType.TIMESTAMP,
                    typeHandler = LocalDateTimeTypeHandler.class),
            @Result(property = "updatedTime", column = "updated_time", jdbcType = JdbcType.TIMESTAMP,
                    typeHandler = LocalDateTimeTypeHandler.class)
    })
    @Select("SELECT * FROM sms_service_provider")
    List<SmsServiceProvider> getSmsServiceProviders();
}
