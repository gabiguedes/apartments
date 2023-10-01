package br.com.guedes.apartments.models;

import br.com.guedes.apartments.models.enums.Role;

import java.io.Serializable;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;

public class UserRequest implements Serializable {

    private Long id;
    private String cpf;
    private String name;
    private String password;
    private Set<Integer> roles = new HashSet<>();

    public UserRequest() {}

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

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public Set<Role> getRoles() {
        return roles.stream().map(Role::toEnum).collect(Collectors.toSet());
    }

    public void setRoles(Set<Integer> roles) {
        this.roles = roles;
    }
}
