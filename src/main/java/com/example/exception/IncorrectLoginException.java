package com.example.exception;

public class IncorrectLoginException extends Exception{
    public IncorrectLoginException(String errormessage){
        super(errormessage);
    }
}
