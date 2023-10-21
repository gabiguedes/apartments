package br.com.guedes.apartments.models.dto.requests;

import br.com.guedes.apartments.models.enums.Role;

public record RequestRegisterDTO(String cpf, String password, Role role) {}
