package com.redhat.training.event;

public class EmployeeSignedUp {
    Long speakerId;
    String fullName;
    String email;
    public EmployeeSignedUp() {}

    public EmployeeSignedUp(Long speakerId, String fullName, String email) {
        this.speakerId = speakerId;
        this.fullName = fullName;
        this.email = email;
    }
}
