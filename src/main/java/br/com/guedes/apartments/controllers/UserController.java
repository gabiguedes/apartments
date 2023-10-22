package br.com.guedes.apartments.controllers;

import br.com.guedes.apartments.models.dto.responses.UserFetcherDTO;
import br.com.guedes.apartments.models.dto.authorization.UserSecurityDetails;
import br.com.guedes.apartments.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    //TODO todos esse metodos vao ser passados para a controller de admin, so ele vai poder acessar e ter essas infos
    @RequestMapping(value = "/get_all_users", method = RequestMethod.GET)
    public ResponseEntity<List<UserFetcherDTO>> getAllUsers() {
       List<UserFetcherDTO> list = userService.getAllUsers();

       return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/get/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<UserFetcherDTO> getByName(@PathVariable String name) {
        UserFetcherDTO userResponse = userService.findByName(name);

        return ResponseEntity.ok().body(userResponse);
    }

    @RequestMapping(value = "/get/cpf/{cpf}", method = RequestMethod.GET)
    public ResponseEntity<UserSecurityDetails> getByCpf(@PathVariable String cpf) {
        UserSecurityDetails userResponse = userService.findByCpf(cpf);

        return ResponseEntity.ok().body(userResponse);
    }

}
