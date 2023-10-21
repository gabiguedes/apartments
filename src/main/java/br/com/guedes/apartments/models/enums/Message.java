package br.com.guedes.apartments.models.enums;

public enum Message {

    CREATED_USER(201, "Successfully registered user"),
    LOGIN_AUTHENTICATED(201, "Token generated successfully, you can browse the app"),
    DUPLICATED_KEY(409, "CPF already registered");
    private final int code;
    private final String description;

    private Message(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }
}
