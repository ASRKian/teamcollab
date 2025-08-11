package com.lokendra.teamcollab.exceptions;

public class ConflictEmailException extends RuntimeException{
    public ConflictEmailException() {
        super("User with this email already exists");
    }    
}
