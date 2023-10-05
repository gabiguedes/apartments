package br.com.guedes.apartments.controllers;

import br.com.guedes.apartments.models.UserRequest;
import br.com.guedes.apartments.models.UserResponse;
import br.com.guedes.apartments.models.enums.Message;
import br.com.guedes.apartments.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<UserResponse> insert(@RequestBody UserRequest user) {
        userService.insertUser(user);

        UserResponse response = new UserResponse();
        response.setCode(HttpStatus.CREATED.value());
        response.setMessage(Message.CREATED_USER.getDescription());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/get_users", method = RequestMethod.GET)
    public ResponseEntity<List<UserRequest>> getAll() {
       List<UserRequest> list = userService.getAllUsers();

       return ResponseEntity.ok().body(list);
    }

}
