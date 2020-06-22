package com.whoisacat.edu;

import org.springframework.context.MessageSource;
import org.springframework.context.MessageSourceResolvable;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.support.ReloadableResourceBundleMessageSource;

import java.util.Locale;

public class Localization implements MessageSource{

    private final ReloadableResourceBundleMessageSource ms;

    public Localization(MessageSourceConfig messageSourceConfig){
        ms = messageSourceConfig.getMessageSource();
    }

    @Override public String getMessage(String code,Object[] args,String defaultMessage,Locale locale){
        return ms.getMessage(code,args,defaultMessage,locale);
    }

    @Override public String getMessage(String code,Object[] args,Locale locale) throws NoSuchMessageException{
        return ms.getMessage(code,args,locale);
    }

    @Override public String getMessage(MessageSourceResolvable resolvable,Locale locale) throws NoSuchMessageException{
        return ms.getMessage(resolvable,locale);
    }
}
