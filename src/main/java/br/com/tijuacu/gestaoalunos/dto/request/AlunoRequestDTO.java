package br.com.tijuacu.gestaoalunos.dto.request;

import br.com.tijuacu.gestaoalunos.model.enums.Sexo;
import br.com.tijuacu.gestaoalunos.model.enums.TipoAee;
import br.com.tijuacu.gestaoalunos.model.enums.TransporteEscolar;
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
        Sexo sexo,                  // MASCULINO / FEMININO
        String corRaca,
        String cartaoSus,
        TransporteEscolar transporteEscolar, // SIM / NAO
        String nomePai,
        String nomeMae,
        TipoAee tipoAee,            // DI, DA, DV, PC, TEA
        @Valid @NotNull EnderecoRequestDTO endereco
) {}