package com.example.exception;

public class InvalidInputException extends Exception{
    public InvalidInputException(String errormessage){
        super(errormessage);
    }
}
