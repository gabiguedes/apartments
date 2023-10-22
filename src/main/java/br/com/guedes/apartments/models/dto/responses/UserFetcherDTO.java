package br.com.guedes.apartments.models.dto.responses;

public record UserFetcherDTO(Long id, String cpf, String name, String role, String creationOnDate) {}