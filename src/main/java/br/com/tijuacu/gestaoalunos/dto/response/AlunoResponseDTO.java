package br.com.tijuacu.gestaoalunos.dto.response;

import br.com.tijuacu.gestaoalunos.model.enums.Sexo;
import br.com.tijuacu.gestaoalunos.model.enums.TipoAee;
import br.com.tijuacu.gestaoalunos.model.enums.TransporteEscolar;

import java.time.LocalDate;

public record AlunoResponseDTO(
        Long id,
        String nomeCompleto,
        LocalDate dataNascimento,
        String rg,
        String cpf,
        Sexo sexo,
        String corRaca,
        String cartaoSus,
        TransporteEscolar transporteEscolar,
        String nomePai,
        String nomeMae,
        TipoAee tipoAee,
        EnderecoResponseDTO endereco
) {}