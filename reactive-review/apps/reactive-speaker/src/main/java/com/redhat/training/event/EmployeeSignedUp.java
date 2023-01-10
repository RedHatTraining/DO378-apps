package com.redhat.training.event;

public class EmployeeSignedUp {
    public Long speakerId;

    public String fullName;

    public String email;

    public EmployeeSignedUp() {}

    public EmployeeSignedUp(Long speakerId, String fullName, String email) {
        this.speakerId = speakerId;
        this.fullName = fullName;
        this.email = email;
    }
}
