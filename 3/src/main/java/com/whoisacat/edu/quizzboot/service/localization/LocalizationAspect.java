package com.whoisacat.edu.quizzboot.service.localization;

import com.whoisacat.edu.quizzboot.service.annotations.Translate;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.context.MessageSource;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Service;

@Aspect
@Service
public class LocalizationAspect{

    private final MessageSource localization;

    public LocalizationAspect(MessageSource localization){
        this.localization = localization;
    }

    @Around(value = "@annotation(com.whoisacat.edu.quizzboot.service.annotations.Translate)")
    public Object localizeIn(ProceedingJoinPoint joinPoint) throws Throwable{
        Translate methodTranslate = ((MethodSignature)joinPoint.getSignature()).getMethod().getAnnotation(
                Translate.class);
        switch(methodTranslate.ioMethod()){
            case OUT:
                return translateOut(joinPoint);
            case IN:
                return translateIn(joinPoint);
            default:
                throw new WHOLacalizationException();
        }
    }

    private Object translateOut(ProceedingJoinPoint joinPoint) throws Throwable{
        String inString = (String)joinPoint.getArgs()[0];
        String localized;
        Object[] args = new Object[joinPoint.getArgs().length];
        for(int i = 1; i < joinPoint.getArgs().length; i++){
            args[i] = joinPoint.getArgs()[i];
        }
        try{
            String[] passingArgsArray;
            if(joinPoint.getArgs().length > 1){
                passingArgsArray = new String[]{"" + (Integer)joinPoint.getArgs()[1]};
            }else {
                passingArgsArray = new String[]{};
            }
            localized = localization.getMessage(inString,passingArgsArray,LocaleContextHolder.getLocale());
        }catch(NoSuchMessageException e){
            localized = inString;
        }
        args[0] = localized;

        return joinPoint.proceed(args);
    }

    private Object translateIn(ProceedingJoinPoint joinPoint) throws Throwable{
        String inString = ((String) joinPoint.proceed(joinPoint.getArgs())).toLowerCase();
        String localized;
        try{
            localized = localization.getMessage(inString,new String[]{},LocaleContextHolder.getLocale());
        }catch(NoSuchMessageException e){
            localized = inString;
        }
        return localized;
    }
}
