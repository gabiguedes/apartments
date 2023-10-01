package br.com.guedes.apartments.models;

import java.io.Serializable;

public class UserResponse implements Serializable {

    private int code;
    private String message;

    public UserResponse() {}

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }
}
