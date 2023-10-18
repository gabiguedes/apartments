package br.com.guedes.apartments.controllers;

import br.com.guedes.apartments.config.beans.security.TokenGenerateService;
import br.com.guedes.apartments.models.dto.AuthResponse;
import br.com.guedes.apartments.models.dto.AuthenticationDTO;
import br.com.guedes.apartments.models.dto.RegisterDTO;
import br.com.guedes.apartments.models.dto.UserSecurityDetails;
import br.com.guedes.apartments.services.UserService;
import org.springframework.beans.factory.annotation.Autowired;
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
    public ResponseEntity login(@RequestBody RegisterDTO registerDTO) {
        UserSecurityDetails user = userService.findByCpf(registerDTO.cpf());

        //if(user.getCpf() != null) {
            // TODO montar obj de resposta para esse caso de user ja está no banco de dados
            System.out.println("user != null");
           // return ResponseEntity.badRequest().build();
       // }

        String bcryptopPassword = new BCryptPasswordEncoder().encode(registerDTO.password());
        UserSecurityDetails newUser = new UserSecurityDetails(registerDTO.cpf(), bcryptopPassword, registerDTO.role());

        userService.saveUserAuthentication(newUser);

        return ResponseEntity.ok().build();
    }

}
