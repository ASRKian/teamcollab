package com.lokendra.teamcollab.exceptions;

public class ProjectNotFoundException extends RuntimeException {
    public ProjectNotFoundException() {
        super("Invalid project id");
    }
}
