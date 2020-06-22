package com.whoisacat.edu;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.boot.autoconfigure.condition.ConditionalOnClass;
import org.springframework.boot.autoconfigure.condition.ConditionalOnMissingBean;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.MessageSource;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import java.util.Locale;

@Configuration
@ConditionalOnClass(MessageSource.class)
@EnableConfigurationProperties(MessageSourceProps.class)
public class MessageSourceAutoConfiguration{
    private static final Logger logger = LoggerFactory.getLogger(MessageSourceAutoConfiguration.class);

    private final MessageSourceProps messageSourceProps;

    public MessageSourceAutoConfiguration(MessageSourceProps messageSourceProps) {
        this.messageSourceProps = messageSourceProps;
    }

    @Bean
    @ConditionalOnMissingBean
    public MessageSourceConfig messageSourceConfig() {
        String basename = messageSourceProps.getBasename() == null ? "classpath:/il8n/bundle" : messageSourceProps.getBasename();
        logger.info("AutoConfig. creating MessageSourceConfig, default basename:{}", basename);
        String defaultEncoding = messageSourceProps.getDefaultEncoding() == null ? "UTF-8" : messageSourceProps.getDefaultEncoding();
        logger.info("AutoConfig. creating MessageSourceConfig, default defaultEncoding:{}", defaultEncoding);
        Locale defaultLocale = messageSourceProps.getDefaultLocale() == null ? Locale.US : messageSourceProps.getDefaultLocale();
        logger.info("AutoConfig. creating MessageSourceConfig, default defaultLocale:{}", defaultLocale);
        return new MessageSourceConfig(basename,defaultEncoding,defaultLocale);
    }

    @Bean
    @ConditionalOnMissingBean
    public Localization localization(MessageSourceConfig messageSourceConfig) {
        logger.info("AutoConfig. creating MessageSource");
        return new Localization(messageSourceConfig);
    }
}
