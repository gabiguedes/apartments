package br.com.guedes.apartments.models.dto;

import br.com.guedes.apartments.models.enums.Role;

public record RegisterDTO(String cpf, String password, Role role) {}
