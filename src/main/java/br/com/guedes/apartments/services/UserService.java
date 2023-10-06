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

    public void insertUser(UserRequest user) {
        dao.insert(user);
    }

    public List<UserResponse> getAllUsers() {
        return dao.getAllUsers();
    }

    public UserResponse findByName(String username) {
       return dao.findByName(username);
    }

}
