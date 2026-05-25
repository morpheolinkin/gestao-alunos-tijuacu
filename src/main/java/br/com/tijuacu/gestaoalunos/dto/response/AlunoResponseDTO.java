package br.com.tijuacu.gestaoalunos.dto.response;

import java.time.LocalDate;

public record AlunoResponseDTO(
        Long id,
        String nomeCompleto,
        LocalDate dataNascimento,
        String rg,
        String cpf,
        String sexo,
        String corRaca,
        String cartaoSus,
        Boolean transporteEscolar,
        String nomePai,
        String nomeMae,
        String tipoAee,
        EnderecoResponseDTO endereco
) {}