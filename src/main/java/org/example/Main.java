package org.example;

//import io.grpc.Server;
//import io.grpc.ServerBuilder;
//import io.grpc.Server;
//import io.grpc.ServerBuilder;
import io.grpc.Server;
import io.grpc.ServerBuilder;
import org.checkerframework.checker.units.qual.A;
import org.example.entities.Node;
import org.example.services.ZabPeerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class Main {

    @Autowired
    private ZabPeerService zabPeerService;

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args); // Start the Spring Boot application
    }

    // This method is called after the Spring context is initialized
    @PostConstruct
    public void init() throws IOException, InterruptedException {
        zabPeerService.initializePeers();
        zabPeerService.logNodeData();
//        Server server = ServerBuilder.forPort(Integer.parseInt(zabPeerService.node.getPort()))
//                .addService(zabPeerService)
//                .build()
//                .start();
        zabPeerService.initializePeers();
        zabPeerService.logNodeData();

    }
}
