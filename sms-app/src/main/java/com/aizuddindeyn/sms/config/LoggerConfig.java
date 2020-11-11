/*
 * Owned by aizuddindeyn
 * Visit https://gitlab.com/group-bear/sms-service
 */
package com.aizuddindeyn.sms.config;

import org.apache.logging.log4j.Level;
import org.apache.logging.log4j.core.LoggerContext;
import org.apache.logging.log4j.core.config.Configuration;
import org.apache.logging.log4j.core.config.ConfigurationFactory;
import org.apache.logging.log4j.core.config.ConfigurationSource;
import org.apache.logging.log4j.core.config.Configurator;
import org.apache.logging.log4j.core.config.Order;
import org.apache.logging.log4j.core.config.builder.api.AppenderComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ComponentBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilder;
import org.apache.logging.log4j.core.config.builder.api.ConfigurationBuilderFactory;
import org.apache.logging.log4j.core.config.builder.api.LayoutComponentBuilder;
import org.apache.logging.log4j.core.config.builder.impl.BuiltConfiguration;
import org.apache.logging.log4j.core.config.plugins.Plugin;
import org.springframework.beans.factory.annotation.Value;

/**
 * @author aizuddindeyn
 * @date 11/9/2020
 */
@Plugin(name = "SmsServiceLogger", category = ConfigurationFactory.CATEGORY)
@Order(50)
public class LoggerConfig extends ConfigurationFactory {

    private static final String APP_NAME = "sms-service";

    private static final String CONSOLE_LOG = "console";

    private static final String ROLLING_LOG = "rollingFile";

    @Value("${log4j.log.pattern}")
    private String logPattern;

    @Value("${log4j.log.path}")
    private String logPath;

    @Value("${log4j.log.filePattern}")
    private String filePattern;

    @Value("${log4j.log.fileSize}")
    private String fileSize;

    @Value("${log4j.log.fileLevel}")
    private String fileLevel;

    @Override
    protected String[] getSupportedTypes() {
        return new String[]{"*"};
    }

    @Override
    public Configuration getConfiguration(LoggerContext loggerContext, ConfigurationSource source) {
        ConfigurationBuilder<BuiltConfiguration> builder = ConfigurationBuilderFactory.newConfigurationBuilder();

        LayoutComponentBuilder layout = builder.newLayout("PatternLayout")
                .addAttribute("pattern", logPattern);

        createConsoleLog(builder, layout);
        createRollingLog(builder, layout);

        builder.newAsyncLogger("com.aizuddindeyn.sms", Level.valueOf(fileLevel))
                .add(builder.newAppenderRef(ROLLING_LOG)).addAttribute("additivity", false);
        builder.newAsyncLogger("org.springframework", Level.INFO)
                .add(builder.newAppenderRef(CONSOLE_LOG)).addAttribute("additivity", false);
        builder.newAsyncRootLogger(Level.ERROR).add(builder.newAppenderRef(CONSOLE_LOG));

        Configurator.initialize(builder.build());
        return builder.build();
    }

    private void createConsoleLog(ConfigurationBuilder<BuiltConfiguration> builder, LayoutComponentBuilder layout) {
        AppenderComponentBuilder console = builder.newAppender(CONSOLE_LOG, "Console")
                .addAttribute("target", "SYSTEM_OUT");
        console.add(layout);

        builder.add(console);
    }

    private void createRollingLog(ConfigurationBuilder<BuiltConfiguration> builder, LayoutComponentBuilder layout) {
        AppenderComponentBuilder rollingFile = builder.newAppender(ROLLING_LOG, "RollingFile");
        rollingFile.addAttribute("fileName", logPath + APP_NAME + ".log");
        rollingFile.addAttribute("filePattern", logPath + APP_NAME + "-" + filePattern);
        rollingFile.addAttribute("ignoreExceptions", false);
        rollingFile.addAttribute("immediateFlush", false);
        rollingFile.add(layout);

        ComponentBuilder policy = builder.newComponent("Policies");
        policy.addComponent(builder.newComponent("TimeBasedTriggeringPolicy"));
        policy.addComponent(builder.newComponent("SizeBasedTriggeringPolicy")
                .addAttribute("size", fileSize));

        builder.add(rollingFile);
    }
}
