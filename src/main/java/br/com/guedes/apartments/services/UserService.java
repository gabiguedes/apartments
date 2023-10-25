package br.com.guedes.apartments.services;

import br.com.guedes.apartments.dao.UserDAO;
import br.com.guedes.apartments.models.dto.responses.UserFetcherDTO;
import br.com.guedes.apartments.models.dto.authorization.UserSecurityDetails;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDAO dao;

    public void saveUserAuthentication(UserSecurityDetails user) {
        dao.insertAuthenticationRegisterUser(user);
    }

    public UserSecurityDetails findByCpf(String cpf) {
        return dao.selectUserDetailsForCPF((cpf));
    }

}