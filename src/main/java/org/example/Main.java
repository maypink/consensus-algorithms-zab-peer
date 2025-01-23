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
        // Print the values injected into the Node bean
//        System.out.println("Node's id is " + node.getId());
//        System.out.println("Node's port is " + node.getPort());

        zabPeerService.initializePeers();
        zabPeerService.logNodeData();

//        zabPeerService.setPeersPortsList(node.getPeerPorts());
//
//        Server server = ServerBuilder.forPort(Integer.parseInt(node.getPort()))  // Specify the gRPC port
//                .addService(new ZabPeerService())  // Add gRPC service implementations
//                .build();
//
//        server.start();
//        System.out.println("gRPC server started on port " + node.getPort());
//
//        server.awaitTermination();
    }
}
