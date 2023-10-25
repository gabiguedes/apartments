package br.com.guedes.apartments.models.enums;

public enum Role {

    ADMIN_SUPREME("ADMIN_SUPREME"),
    USER_NOOB("USER_NOOB");

    private String role;

    Role(String role) {
        this.role = role;
    }

    public String getRole() {
        return role;
    }

}