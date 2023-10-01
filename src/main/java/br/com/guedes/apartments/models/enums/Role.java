package br.com.guedes.apartments.models.enums;

public enum Role {

    ADMIN_SUPREME(1, "ROLE_ADMIN_SUPREME"), USER_NOOB(2, "ROLE_USER_NOOB");

    private final int code;
    private final String description;

    private Role(int code, String description) {
        this.code = code;
        this.description = description;
    }

    public int getCode() {
        return code;
    }

    public String getDescription() {
        return description;
    }

    public static Role toEnum(Integer cod) {

        if (cod == null) {
            return null;
        }

        for (Role x : Role.values()) {
            if (cod.equals(x.getCode())) {
                return x;
            }
        }

        throw new IllegalArgumentException("Invalid ID: " + cod);

    }
}