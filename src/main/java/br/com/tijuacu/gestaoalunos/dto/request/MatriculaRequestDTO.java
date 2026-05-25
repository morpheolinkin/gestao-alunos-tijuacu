package br.com.tijuacu.gestaoalunos.dto.request;

import br.com.tijuacu.gestaoalunos.model.enums.SituacaoMatricula;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;

public record MatriculaRequestDTO(
        Integer numero,
        @NotNull Integer anoLetivo,
        @NotNull LocalDate dataMatricula,
        @NotNull SituacaoMatricula situacao,
        @NotNull Long alunoId,
        @NotNull Long turmaId
) {}