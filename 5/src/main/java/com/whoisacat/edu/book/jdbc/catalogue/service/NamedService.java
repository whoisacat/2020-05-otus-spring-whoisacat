package com.whoisacat.edu.book.jdbc.catalogue.service;

import com.whoisacat.edu.book.jdbc.catalogue.domain.Named;

import java.util.List;

public interface NamedService<T extends Named>{

    default String buildNames(List<T> objects){
        StringBuilder builder = new StringBuilder();
        for(T object : objects){
            builder.append(object.getName())
                    .append(';')
                    .append(' ');
        }
        return builder.toString();
    }

}
