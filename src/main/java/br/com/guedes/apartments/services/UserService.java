package br.com.guedes.apartments.services;

import br.com.guedes.apartments.dao.UserDAO;
import br.com.guedes.apartments.models.UserRequest;
import br.com.guedes.apartments.models.dto.UserResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class UserService {

    @Autowired
    UserDAO dao;

    public void saveUser(UserRequest user) {
        dao.insert(user);
    }

    public List<UserResponse> getAllUsers() {
        return dao.selectAllUsers();
    }

    public UserResponse findByName(String username) {
       return dao.selectUserForName(username);
    }

    public UserResponse findByCpf(String cpf) {
        return dao.selectUserForCPF((cpf));
    }

}