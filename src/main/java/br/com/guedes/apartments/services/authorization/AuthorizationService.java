package br.com.guedes.apartments.services.authorization;

import br.com.guedes.apartments.dao.UserDAO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

@Service
public class AuthorizationService implements UserDetailsService {

    @Autowired
    UserDAO dao;

    @Override
    public UserDetails loadUserByUsername(String cpf) throws UsernameNotFoundException {
        return dao.selectUserForCPF(cpf);
    }
}
