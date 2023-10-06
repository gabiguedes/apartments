package br.com.guedes.apartments.models;

import br.com.guedes.apartments.models.enums.Role;

import java.io.Serializable;

public class UserRequest implements Serializable {

    private Long id;
    private String cpf;
    private String name;
    private String password;
    private Role role;
    public UserRequest() {}

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

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }
}
