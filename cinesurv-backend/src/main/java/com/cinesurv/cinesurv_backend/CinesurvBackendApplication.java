package com.cinesurv.cinesurv_backend;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

@SpringBootApplication
public class CinesurvBackendApplication {

    public static void main(String[] args) {
        SpringApplication.run(CinesurvBackendApplication.class, args);
        System.out.println("=================================================");
        System.out.println(" Cinesurv Backend is running on port 8080");
        System.out.println(" REST API:    http://localhost:8080/api/alerts");
        System.out.println(" WebSocket:   ws://localhost:8080/ws");
        System.out.println(" H2 Console:  http://localhost:8080/h2-console");
        System.out.println("=================================================");
    }
}