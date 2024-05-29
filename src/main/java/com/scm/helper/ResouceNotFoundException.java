package com.scm.helper;

public class ResouceNotFoundException extends RuntimeException {

    public ResouceNotFoundException(String message){
        super(message);
    }
    public ResouceNotFoundException(){
        super("Resouce not found...");
    }

}
