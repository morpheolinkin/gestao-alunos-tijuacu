package br.com.tijuacu.gestaoalunos.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AlunoRequestDTO(
        @NotBlank String nomeCompleto,
        @NotNull @Past LocalDate dataNascimento,
        String cpf,
        String sexo,
        String corRaca,
        Boolean aee,
        @Valid @NotNull EnderecoRequestDTO endereco
) {}