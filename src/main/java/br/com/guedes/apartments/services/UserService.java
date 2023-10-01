package br.com.guedes.apartments.services;

import br.com.guedes.apartments.dao.UserDAO;
import br.com.guedes.apartments.models.UserRequest;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class UserService {

    @Autowired
    UserDAO dao;

    public void insertUser(UserRequest user) {
        dao.insert(user);
    }

}
