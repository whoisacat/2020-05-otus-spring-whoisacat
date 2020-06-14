package com.whoisacat.edu.quizzboot.service.localization;

import com.whoisacat.edu.Localization;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.springframework.context.NoSuchMessageException;
import org.springframework.context.i18n.LocaleContextHolder;
import org.springframework.stereotype.Component;

@Aspect
@Component
public class LocalizationAspect{

    private final Localization localization;

    public LocalizationAspect(Localization localization){
        this.localization = localization;
    }

    @Around("execution(* com.whoisacat.edu.quizzboot.service.ui.PrinterService.*(..))")
    public Object localizeAround(ProceedingJoinPoint joinPoint) throws Throwable{
        String inString = (String)joinPoint.getArgs()[0];
        String localized;
        Object[] args = new Object[joinPoint.getArgs().length];
        for(int i = 1; i < joinPoint.getArgs().length; i++){
            args[i] = joinPoint.getArgs()[i];
        }
        try{
            String[] passingArgsArray;
            if(joinPoint.getArgs().length > 1 && ((int[])joinPoint.getArgs()[1]).length > 0){
                passingArgsArray = new String[]{"" + ((int[])joinPoint.getArgs()[1])[0]};
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
    @Around("execution(* com.whoisacat.edu.quizzboot.service.ui.ReaderService.readString())")
    public Object localizeInAround(ProceedingJoinPoint joinPoint) throws Throwable{
        String inString = (String) joinPoint.proceed(joinPoint.getArgs());
        String localized;
        try{
            localized = localization.getMessage(inString,new String[]{},LocaleContextHolder.getLocale());
        }catch(NoSuchMessageException e){
            localized = inString;
        }
        return localized;
    }
}
