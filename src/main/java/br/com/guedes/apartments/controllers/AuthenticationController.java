package br.com.guedes.apartments.controllers;

import br.com.guedes.apartments.config.security.TokenGenerateService;
import br.com.guedes.apartments.models.dto.responses.DefaultResponseDTO;
import br.com.guedes.apartments.models.dto.responses.LoginTokenResponseDTO;
import br.com.guedes.apartments.models.dto.authorization.RequestLoginDTO;
import br.com.guedes.apartments.models.dto.authorization.RequestRegisterDTO;
import br.com.guedes.apartments.models.dto.authorization.UserSecurityDetails;
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
    private TokenGenerateService tokenGenerateService;

    @RequestMapping(value = "/login", method = RequestMethod.POST)
    public ResponseEntity<LoginTokenResponseDTO> login(@RequestBody RequestLoginDTO authentication) {
        var cpfPassword = new UsernamePasswordAuthenticationToken(authentication.cpf(), authentication.password());
        var auth = this.authenticationManager.authenticate(cpfPassword);

        var token = tokenGenerateService.generateToken((UserSecurityDetails) auth.getPrincipal());
        return new ResponseEntity<>(new LoginTokenResponseDTO(
                token,
                Message.LOGIN_AUTHENTICATED.getDescription()),
                HttpStatus.OK);
    }

    @RequestMapping(value = "/register", method = RequestMethod.POST)
    public ResponseEntity<DefaultResponseDTO> register(@RequestBody RequestRegisterDTO registerDTO) {
        UserSecurityDetails user = userService.findByCpf(registerDTO.cpf());
        if(user != null && user.getCpf() != null) {
            return new ResponseEntity<>(new DefaultResponseDTO(
                    HttpStatus.CONFLICT.value(),
                    Message.DUPLICATED_KEY.getDescription()),
                    HttpStatus.CONFLICT);
       }

        String encryptedPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        UserSecurityDetails newUser = new UserSecurityDetails(
                registerDTO.cpf(),
                encryptedPassword,
                registerDTO.role(),
                registerDTO.name(),
                registerDTO.creationOnDate());
        userService.saveUserAuthentication(newUser);

        return new ResponseEntity<>(new DefaultResponseDTO(
                HttpStatus.CREATED.value(),
                Message.CREATED_USER.getDescription()),
                HttpStatus.OK);
    }

}