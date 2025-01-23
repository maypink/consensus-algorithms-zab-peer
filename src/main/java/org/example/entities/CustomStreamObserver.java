package org.example.entities;

import io.grpc.stub.StreamObserver;

public class CustomStreamObserver implements StreamObserver {
    @Override
    public void onNext(Object o) {
        System.out.println("onNext is called");
    }

    @Override
    public void onError(Throwable throwable) {

    }

    @Override
    public void onCompleted() {
        System.out.println("onCompleted is called");
    }
}
