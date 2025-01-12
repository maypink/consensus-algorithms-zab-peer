package org.example;

//import io.grpc.Server;
//import io.grpc.ServerBuilder;
//import io.grpc.Server;
//import io.grpc.ServerBuilder;
import org.example.entities.Node;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

import javax.annotation.PostConstruct;
import java.io.IOException;

@SpringBootApplication
public class Main {

    @Autowired
    private Node node; // Node bean injected by Spring

    public static void main(String[] args) {
        SpringApplication.run(Main.class, args); // Start the Spring Boot application
    }

    // This method is called after the Spring context is initialized
    @PostConstruct
    public void init() throws IOException, InterruptedException {
        // Print the values injected into the Node bean
        System.out.println("Node's id is " + node.getId());
        System.out.println("Node's port is " + node.getPort());

//        // Start the gRPC server
//        Server server = ServerBuilder.forPort(Integer.parseInt(node.getPort()))
//                .build()
//                .start();
//        System.out.println("Server " + node.getId() + " started on port " + node.getPort());
//
//        // Keep the server running
//        server.awaitTermination();
    }
}
