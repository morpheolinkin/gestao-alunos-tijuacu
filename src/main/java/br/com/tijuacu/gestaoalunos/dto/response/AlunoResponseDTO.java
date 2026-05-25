package br.com.tijuacu.gestaoalunos.dto.response;

import java.time.LocalDate;

public record AlunoResponseDTO(
        Long id,
        String nomeCompleto,
        LocalDate dataNascimento,
        String cpf,
        String sexo,
        String corRaca,
        Boolean aee,
        EnderecoResponseDTO endereco
) {}