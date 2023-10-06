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
        return false;
    }

    @Override
    public boolean isAccountNonLocked() {
        return false;
    }

    @Override
    public boolean isCredentialsNonExpired() {
        return false;
    }

    @Override
    public boolean isEnabled() {
        return false;
    }
}
