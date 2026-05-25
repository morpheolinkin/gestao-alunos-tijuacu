package br.com.tijuacu.gestaoalunos.dto.response;

import br.com.tijuacu.gestaoalunos.model.enums.Modalidade;
import br.com.tijuacu.gestaoalunos.model.enums.Turno;

public record TurmaResponseDTO(
        Long id,
        Integer anoLetivo,
        String nome,
        String etapa,
        Turno turno,
        Modalidade modalidade,
        Integer capacidadeMaxima,
        Boolean ativa
) {}