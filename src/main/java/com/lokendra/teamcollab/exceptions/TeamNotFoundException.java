package com.lokendra.teamcollab.exceptions;

public class TeamNotFoundException extends RuntimeException {
    public TeamNotFoundException() {
        super("Invalid team id");
    }
}
