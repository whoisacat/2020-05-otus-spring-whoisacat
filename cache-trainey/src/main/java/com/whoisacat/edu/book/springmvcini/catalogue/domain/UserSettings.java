package com.whoisacat.edu.book.springmvcini.catalogue.domain;

public class UserSettings{
    private int rowsPerPage;

    public int getRowsPerPage(){
        return rowsPerPage;
    }

    public void setRowsPerPage(int rowsPerPage){
        this.rowsPerPage = rowsPerPage;
    }
}
