/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.common;

import com.aizuddindeyn.sms.enums.StatusCodeEnum;
import com.aizuddindeyn.sms.util.Fields;
import com.aizuddindeyn.sms.util.LogLevelHelper;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.datatype.jsr310.JavaTimeModule;
import com.google.common.collect.ImmutableMap;
import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.MDC;

import javax.servlet.http.HttpServletRequest;
import java.io.IOException;
import java.util.Map;

import static com.aizuddindeyn.sms.util.Fields.DEBUG_ID;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
public abstract class AbstractController<R extends BaseRequest, P extends BaseResult> {

    private static final String X_FORWARDED_FOR = "x-forwarded-for";

    /**
     * Send method to be set as controller endpoint
     *
     * @param request        Request object
     * @param servletRequest HTTP Servlet Request
     * @return Map that will be converted to JSON by Spring
     */
    public Map<String, Object> send(R request, HttpServletRequest servletRequest) {

        MDC.put(DEBUG_ID, LogLevelHelper.generateDebugId());
        Map<String, Object> result;
        ObjectMapper mapper = new ObjectMapper();
        mapper.registerModule(new JavaTimeModule());
        String ipAddress =
                StringUtils.defaultString(servletRequest.getHeader(X_FORWARDED_FOR), servletRequest.getRemoteAddr());

        try {
            String jsonRequest = mapper.writer().writeValueAsString(request);
            LogLevelHelper.logInfo(getLogger(), "Received {} from IP {} with request: {}",
                    getFunction().concat("Request"), ipAddress, jsonRequest);

            Object response = process(request);

            result = mapper.convertValue(response, new TypeReference<Map<String, Object>>() {
            });

        } catch (IOException ex) {
            LogLevelHelper.logError(getLogger(), "Invalid request format received on {} from {}",
                    getFunction().concat("Request"), ipAddress);

            result = constructResponse(StatusCodeEnum.INVALID_REQUEST, "Invalid request format");

        } catch (Exception ex) {
            LogLevelHelper.logError(getLogger(), "Unexpected Error: " + ex.getMessage(), ex);

            result = constructResponse(StatusCodeEnum.SYSTEM_ERROR, "Unexpected Error: " + ex.getMessage());
        }

        String jsonResponse = "";
        try {
            jsonResponse = mapper.writer().writeValueAsString(result);
        } catch (JsonProcessingException ex) {
            //Ignore
        } finally {
            LogLevelHelper.logInfo(getLogger(), "Sending response: {}", jsonResponse);
        }

        MDC.clear();
        return result;
    }

    /**
     * Processing logic of endpoint request
     *
     * @param request Request object
     * @return Response object
     */
    protected abstract P process(R request) throws Exception;

    protected abstract Logger getLogger();

    protected String getFunction() {
        return this.getClass().getSimpleName();
    }

    protected Map<String, Object> constructResponse(StatusCodeEnum status, String message) {
        ImmutableMap.Builder<String, Object> builder = ImmutableMap.builder();

        return builder
                .put(Fields.STATUS_CODE, status.getCode())
                .put(Fields.MESSAGE, message)
                .put(Fields.DATA, "")
                .put(Fields.SERVICE_PROVIDER, "")
                .build();
    }
}
