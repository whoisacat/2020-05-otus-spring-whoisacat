package com.whoisacat.edu.book.springdata.catalogue;

import org.h2.tools.Console;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.properties.EnableConfigurationProperties;

import java.sql.SQLException;

@SpringBootApplication
@EnableConfigurationProperties
public class Main {

    public static void main(String[] args) throws SQLException{
        SpringApplication.run(Main.class,args);
        Console.main(args);
    }
}
