package br.com.guedes.apartments.models.dto;

import java.io.Serializable;
import java.util.Date;

public class UserResponse implements Serializable {

    private Long id;
    private String cpf;
    private String name;
    private String role;

    private String creationOnDate;

    public UserResponse() {}

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getCpf() {
        return cpf;
    }

    public void setCpf(String cpf) {
        this.cpf = cpf;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getRole() {
        return role;
    }

    public void setRole(String role) {
        this.role = role;
    }

    public String getCreationOnDate() {
        return creationOnDate;
    }

    public void setCreationOnDate(String creationOnDate) {
        this.creationOnDate = creationOnDate;
    }
}