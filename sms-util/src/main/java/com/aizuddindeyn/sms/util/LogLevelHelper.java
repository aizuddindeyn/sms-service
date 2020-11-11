/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.util;

import org.slf4j.Logger;

import java.util.UUID;

/**
 * @author aizuddindeyn
 * @date 11/5/2020
 */
public class LogLevelHelper {

    private LogLevelHelper() {
        throw new IllegalStateException("Utils class");
    }

    public static String generateDebugId() {
        return UUID.randomUUID().toString().replace("-", "").toUpperCase();
    }

    public static void logError(Logger logger, String msg) {
        if (logger.isErrorEnabled()) {
            logger.error(msg);
        }
    }

    public static void logError(Logger logger, String msg, Throwable t) {
        if (logger.isErrorEnabled()) {
            logger.error(msg, t);
        }
    }

    public static void logError(Logger logger, String format, Object... arguments) {
        if (logger.isErrorEnabled()) {
            logger.error(format, arguments);
        }
    }

    public static void logWarn(Logger logger, String msg) {
        if (logger.isWarnEnabled()) {
            logger.warn(msg);
        }
    }

    public static void logWarn(Logger logger, String msg, Throwable t) {
        if (logger.isWarnEnabled()) {
            logger.warn(msg, t);
        }
    }

    public static void logWarn(Logger logger, String format, Object... arguments) {
        if (logger.isWarnEnabled()) {
            logger.warn(format, arguments);
        }
    }

    public static void logInfo(Logger logger, String msg) {
        if (logger.isInfoEnabled()) {
            logger.info(msg);
        }
    }

    public static void logInfo(Logger logger, String msg, Throwable t) {
        if (logger.isInfoEnabled()) {
            logger.info(msg, t);
        }
    }

    public static void logInfo(Logger logger, String format, Object... arguments) {
        if (logger.isInfoEnabled()) {
            logger.info(format, arguments);
        }
    }

    public static void logDebug(Logger logger, String msg) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg);
        }
    }

    public static void logDebug(Logger logger, String msg, Throwable t) {
        if (logger.isDebugEnabled()) {
            logger.debug(msg, t);
        }
    }

    public static void logDebug(Logger logger, String format, Object... arguments) {
        if (logger.isDebugEnabled()) {
            logger.debug(format, arguments);
        }
    }
}
