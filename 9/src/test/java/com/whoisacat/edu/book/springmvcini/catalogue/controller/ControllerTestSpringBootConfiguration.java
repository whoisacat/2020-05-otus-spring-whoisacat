package com.whoisacat.edu.book.springmvcini.catalogue.controller;

import org.springframework.boot.SpringBootConfiguration;
import org.springframework.context.annotation.ComponentScan;

@ComponentScan(value = {"com.whoisacat.edu.book.springmvcini.catalogue.repository",
        "com.whoisacat.edu.book.springmvcini.catalogue.service",
        "com.whoisacat.edu.book.springmvcini.catalogue.controller"})
@SpringBootConfiguration
public class ControllerTestSpringBootConfiguration {

}
