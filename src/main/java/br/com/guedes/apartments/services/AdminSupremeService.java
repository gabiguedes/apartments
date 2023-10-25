package br.com.guedes.apartments.services;

import br.com.guedes.apartments.dao.AdminSupremeDAO;
import br.com.guedes.apartments.models.dto.responses.UserFetcherDTO;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class AdminSupremeService {

    @Autowired
    AdminSupremeDAO adminSupremeDAO;

    public List<UserFetcherDTO> getAllUsers() {
        return adminSupremeDAO.selectAllUsers();
    }

    public UserFetcherDTO findByName(String username) {
        return adminSupremeDAO.selectUserForName(username);
    }

    public UserFetcherDTO findByCpf(String cpf) {
        return adminSupremeDAO.selectUserForCpf((cpf));
    }
}
