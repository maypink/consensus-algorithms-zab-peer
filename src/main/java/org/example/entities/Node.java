package org.example.entities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zab.History;
import zab.State;


@Component
public class Node {

    @Value("${id}")
    private Integer id;

    private State state;

    private Integer leaderId;

    private History history;

    @Value("${port}")
    private String port;

    public Integer getId() {
        return id;
    }

    public void setId(Integer id) {
        this.id = id;
    }

    public State getState() {
        return state;
    }

    public void setState(State state) {
        this.state = state;
    }

    public History getHistory() {
        return history;
    }

    public void setHistory(History history) {
        this.history = history;
    }

    public String getPort() {
        return port;
    }

    public void setPort(String port) {
        this.port = port;
    }

    public Integer getLeaderId() {
        return leaderId;
    }

    public void setLeaderId(Integer leaderId) {
        this.leaderId = leaderId;
    }
}
