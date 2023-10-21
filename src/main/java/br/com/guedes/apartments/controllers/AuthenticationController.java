package br.com.guedes.apartments.controllers;

import br.com.guedes.apartments.config.beans.security.TokenGenerateService;
import br.com.guedes.apartments.models.dto.DefaultResponseDTO;
import br.com.guedes.apartments.models.dto.AuthResponse;
import br.com.guedes.apartments.models.dto.AuthenticationDTO;
import br.com.guedes.apartments.models.dto.RegisterDTO;
import br.com.guedes.apartments.models.dto.UserSecurityDetails;
import br.com.guedes.apartments.models.enums.Message;
import br.com.guedes.apartments.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    @Autowired
    private AuthenticationManager authenticationManager;

    @Autowired
    private UserService userService;

    @Autowired
    TokenGenerateService tokenGenerateService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity login(@RequestBody AuthenticationDTO authentication) {
        var cpfPassword = new UsernamePasswordAuthenticationToken(authentication.cpf(), authentication.password());
        var auth = this.authenticationManager.authenticate(cpfPassword);

        var token = tokenGenerateService.generateToken((UserSecurityDetails) auth.getPrincipal());
        return ResponseEntity.ok(new AuthResponse(token));
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<DefaultResponseDTO> register(@RequestBody RegisterDTO registerDTO) {
        UserSecurityDetails user = userService.findByCpf(registerDTO.cpf());
        if(user != null && user.getCpf() != null) {
            return new ResponseEntity<>(new DefaultResponseDTO(
                    HttpStatus.CONFLICT.value(),
                    Message.DUPLICATED_KEY.getDescription()),
                    HttpStatus.CONFLICT);
       }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        UserSecurityDetails newUser = new UserSecurityDetails(registerDTO.cpf(), encryptedPassword, registerDTO.role());
        userService.saveUserAuthentication(newUser);

        return new ResponseEntity<>(new DefaultResponseDTO(
                HttpStatus.CREATED.value(),
                Message.CREATED_USER.getDescription()),
                HttpStatus.OK);
    }

}