package com.domoticswot;

import org.apache.jena.fuseki.main.FusekiServer;
import org.apache.jena.fuseki.server.DataService;
import org.apache.jena.query.Dataset;
import org.apache.jena.query.DatasetFactory;
import org.apache.jena.sparql.core.DatasetGraph;
import org.springframework.boot.CommandLineRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;
import org.springframework.scheduling.annotation.EnableScheduling;

import javax.annotation.PostConstruct;

@SpringBootApplication
@EnableScheduling

public class App {
    public static void main(String[] args) {
        SpringApplication.run(App.class, args);
    }

    @PostConstruct
    private void init(){
        System.out.println("Iniciando app");

        Dataset ds = DatasetFactory.create();
        FusekiServer server = FusekiServer.create()
                .port(3332)
                .add("/ds", ds, true)
                .build() ;
        server.start() ;
    }
}