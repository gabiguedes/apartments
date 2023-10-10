package br.com.guedes.apartments.controllers;

import br.com.guedes.apartments.models.UserRequest;
import br.com.guedes.apartments.models.DefaultResponse;
import br.com.guedes.apartments.models.dto.UserResponse;
import br.com.guedes.apartments.models.dto.UserSecurityDetails;
import br.com.guedes.apartments.models.enums.Message;
import br.com.guedes.apartments.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/register_user", method = RequestMethod.POST)
    public ResponseEntity<DefaultResponse> registerUser(@RequestBody UserRequest user) {
        userService.saveUser(user);

        DefaultResponse response = new DefaultResponse();
        response.setCode(HttpStatus.CREATED.value());
        response.setMessage(Message.CREATED_USER.getDescription());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/get_all_users", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>> getAllUsers() {
       List<UserResponse> list = userService.getAllUsers();

       return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/get/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<UserResponse> getByName(@PathVariable String name) {
        UserResponse userResponse = userService.findByName(name);

        return ResponseEntity.ok().body(userResponse);
    }

    @RequestMapping(value = "/get/cpf/{cpf}", method = RequestMethod.GET)
    public ResponseEntity<UserSecurityDetails> getByCpf(@PathVariable String cpf) {
        UserSecurityDetails userResponse = userService.findByCpf(cpf);

        return ResponseEntity.ok().body(userResponse);
    }

}
