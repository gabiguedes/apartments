package br.com.guedes.apartments.controllers;

import br.com.guedes.apartments.models.dto.responses.UserFetcherDTO;
import br.com.guedes.apartments.services.AdminSupremeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/admin")
public class AdminSupremeController {

    @Autowired
    AdminSupremeService adminSupremeService;

    @RequestMapping(value = "/all_users", method = RequestMethod.GET)
    public ResponseEntity<List<UserFetcherDTO>> getAllUsers() {
        List<UserFetcherDTO> list = adminSupremeService.getAllUsers();

        return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<UserFetcherDTO> getByName(@PathVariable String name) {
        UserFetcherDTO userResponse = adminSupremeService.findByName(name);

        return ResponseEntity.ok().body(userResponse);
    }

    @RequestMapping(value = "/cpf/{cpf}", method = RequestMethod.GET)
    public ResponseEntity<UserFetcherDTO> getByCpf(@PathVariable String cpf) {
        UserFetcherDTO userResponse = adminSupremeService.findByCpf(cpf);

        return ResponseEntity.ok().body(userResponse);
    }

}