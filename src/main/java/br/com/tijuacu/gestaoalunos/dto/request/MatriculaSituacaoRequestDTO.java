package br.com.tijuacu.gestaoalunos.dto.request;

import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MatriculaSituacaoRequestDTO(
        @NotNull LocalDate dataSaida,
        String motivoSaida
) {}