package br.com.guedes.apartments.models.dto;

import br.com.guedes.apartments.models.enums.Role;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.userdetails.UserDetails;

import java.util.Collection;
import java.util.List;

public class UserSecurityDetails implements UserDetails {

    private Long id;
    private String cpf;
    private String password;
    private Role role;

    public UserSecurityDetails() {}

    public UserSecurityDetails(String cpf, String bcryptopPassword, Role role) {
        this.cpf = cpf;
        this.password = bcryptopPassword;
        this.role = role;
    }

    @Override
    public Collection<? extends GrantedAuthority> getAuthorities() {
        if (this.role == Role.ADMIN_SUPREME) {
            return List.of(new SimpleGrantedAuthority("ROLE_ADMIN_SUPREME"), new SimpleGrantedAuthority("ROLE_USER_NOOB"));
        }

        return List.of(new SimpleGrantedAuthority("ROLE_USER_NOOB"));
    }

    @Override
    public String getUsername() {
        return cpf;
    }

    @Override
    public String getPassword() {
        return password;
    }

    @Override
    public boolean isAccountNonExpired() {
        return true;
    }

    @Override
    public boolean isAccountNonLocked() {
        return true;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return true;
    }

    @Override
    public boolean isEnabled() {
        return true;
    }

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

    public void setPassword(String password) {
        this.password = password;
    }

    public Role getRole() {
        return role;
    }

    public void setRole(Role role) {
        this.role = role;
    }
}
