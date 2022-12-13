package com.redhat.training.service;

import javax.enterprise.context.ApplicationScoped;

@ApplicationScoped
public class StateService {
    private boolean alive = true;

    public void up() {
        alive = true;
    }

    public void down() {
        alive = false;
    }

    public boolean isAlive() {
        return alive;
    }
}
