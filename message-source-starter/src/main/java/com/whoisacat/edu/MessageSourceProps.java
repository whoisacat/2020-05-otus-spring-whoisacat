package com.whoisacat.edu;

import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.Locale;

@ConfigurationProperties(prefix="com.whoisacat.edu.message-source")
public class MessageSourceProps{

    private String basename;
    private String defaultEncoding;
    private Locale defaultLocale;

    public String getBasename() {
        return basename;
    }

    public void setBasename(String basename) {
        this.basename = basename;
    }

    public String getDefaultEncoding(){
        return defaultEncoding;
    }

    public void setDefaultEncoding(String defaultEncoding){
        this.defaultEncoding = defaultEncoding;
    }

    public Locale getDefaultLocale(){
        return defaultLocale;
    }

    public void setDefaultLocale(Locale defaultLocale){
        this.defaultLocale = defaultLocale;
    }
}
