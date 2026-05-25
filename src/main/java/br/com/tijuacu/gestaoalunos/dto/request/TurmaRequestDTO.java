package br.com.tijuacu.gestaoalunos.dto.request;

import br.com.tijuacu.gestaoalunos.model.enums.Modalidade;
import br.com.tijuacu.gestaoalunos.model.enums.Turno;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;

public record TurmaRequestDTO(
        @NotNull Integer anoLetivo,
        @NotBlank String nome,
        String etapa,
        @NotNull Turno turno,
        @NotNull Modalidade modalidade,
        @Min(1) Integer capacidadeMaxima
) {}