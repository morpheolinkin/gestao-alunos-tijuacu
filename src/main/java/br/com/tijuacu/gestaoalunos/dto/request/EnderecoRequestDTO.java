package br.com.tijuacu.gestaoalunos.dto.request;

import jakarta.validation.constraints.NotBlank;

public record EnderecoRequestDTO(
        @NotBlank String logradouro,
        @NotBlank String numero,
        @NotBlank String bairro,
        String distrito,
        @NotBlank String cidade,
        String cep,
        @NotBlank String zona // urbano / rural
) {}