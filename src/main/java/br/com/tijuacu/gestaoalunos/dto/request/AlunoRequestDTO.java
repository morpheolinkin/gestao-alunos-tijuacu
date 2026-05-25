package br.com.tijuacu.gestaoalunos.dto.request;

import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AlunoRequestDTO(
        @NotBlank String nomeCompleto,
        @NotNull @Past LocalDate dataNascimento,
        String rg,
        @NotBlank String cpf,
        String sexo,         // "M" ou "F"
        String corRaca,
        String cartaoSus,
        Boolean transporteEscolar,
        String nomePai,
        String nomeMae,
        String tipoAee,      // "DI", "DA", "DV", "PC", "TEA" ou vazio
        @Valid @NotNull EnderecoRequestDTO endereco
) {}