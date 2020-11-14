package com.whoisacat.edu.book.springmvcini.catalogue;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import java.sql.SQLException;

@SpringBootApplication
public class WebLibraryLauncher{

    public static void main(String[] args) {
        SpringApplication.run(WebLibraryLauncher.class,args);
    }
}
