package com.whoisacat.edu.book.catalogue.service;

import com.whoisacat.edu.book.catalogue.domain.Named;

import java.util.List;

public interface NamedService<T extends Named>{

    default StringBuilder buildNames(List<T> objects){
        StringBuilder builder = new StringBuilder();
        for(T object : objects){
            builder.append(object.getName())
                    .append(';')
                    .append(' ');
        }
        return builder;
    }

}
