package br.com.tijuacu.gestaoalunos.dto.response;

import br.com.tijuacu.gestaoalunos.model.enums.SituacaoMatricula;

import java.time.LocalDate;

public record MatriculaResponseDTO(
        Long id,
        Integer numero,
        Integer anoLetivo,
        LocalDate dataMatricula,
        SituacaoMatricula situacao,
        Long alunoId,
        String alunoNome,
        Long turmaId,
        String turmaNome
) {}