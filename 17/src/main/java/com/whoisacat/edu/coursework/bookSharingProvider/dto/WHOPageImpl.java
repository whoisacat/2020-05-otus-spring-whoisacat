package com.whoisacat.edu.coursework.bookSharingProvider.dto;

import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;

import java.util.List;

public class WHOPageImpl<T extends Object> extends PageImpl<T> {

    private final String searchText;

    public WHOPageImpl(List<T> resultList, Pageable pageable, long total, String searchText) {
        super(resultList, pageable, total);
        this.searchText = searchText;
    }

    public String getSearchText() {
        return searchText;
    }
}
