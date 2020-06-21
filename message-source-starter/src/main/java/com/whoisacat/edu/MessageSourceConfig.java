package com.whoisacat.edu;

import org.springframework.context.support.ReloadableResourceBundleMessageSource;
import java.util.Locale;

public class MessageSourceConfig {

    private final ReloadableResourceBundleMessageSource ms;

    public MessageSourceConfig(String basename,String defaultEncoding,Locale defaultLocale){
        ms = new ReloadableResourceBundleMessageSource();
        ms.setBasename(basename);
        ms.setDefaultEncoding(defaultEncoding);
        ms.setDefaultLocale(defaultLocale);
    }

    public ReloadableResourceBundleMessageSource getMessageSource(){
        return ms;
    }
}
