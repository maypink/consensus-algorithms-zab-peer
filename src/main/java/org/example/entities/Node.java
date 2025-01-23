package org.example.entities;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;
import zab.History;
import zab.State;

import javax.annotation.PostConstruct;
import java.util.Arrays;
import java.util.List;


@Component
public class Node {

    @Value("${id}")
    private Integer id;

    private State state;

    private Integer leaderId;

    private History history;

    @Value("${port}")
    private String port;

    @Value("${peers}")
    private String ports;

    private List<String> peersPortsList;

    @PostConstruct
    public void init() {
        // Convert the comma-separated string to a List
        peersPortsList = Arrays.asList(ports.split(","));
        state = State.Election;
        history = History.getDefaultInstance();
    }

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

    public List<String> getPeerPorts() {
        return peersPortsList;
    }

}
