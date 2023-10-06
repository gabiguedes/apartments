package br.com.guedes.apartments.controllers;

import br.com.guedes.apartments.models.UserRequest;
import br.com.guedes.apartments.models.DefaultResponse;
import br.com.guedes.apartments.models.dto.UserResponse;
import br.com.guedes.apartments.models.enums.Message;
import br.com.guedes.apartments.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/users")
public class UserController {

    @Autowired
    UserService userService;

    @RequestMapping(value = "/insert", method = RequestMethod.POST)
    public ResponseEntity<DefaultResponse> insert(@RequestBody UserRequest user) {
        userService.insertUser(user);

        DefaultResponse response = new DefaultResponse();
        response.setCode(HttpStatus.CREATED.value());
        response.setMessage(Message.CREATED_USER.getDescription());

        return new ResponseEntity<>(response, HttpStatus.CREATED);
    }

    @RequestMapping(value = "/get_users", method = RequestMethod.GET)
    public ResponseEntity<List<UserResponse>> getAll() {
       List<UserResponse> list = userService.getAllUsers();

       return ResponseEntity.ok().body(list);
    }

    @RequestMapping(value = "/find/name/{name}", method = RequestMethod.GET)
    public ResponseEntity<UserResponse> getByName(@PathVariable String name) {
        UserResponse list = userService.findByName(name);

        return ResponseEntity.ok().body(list);
    }

}
