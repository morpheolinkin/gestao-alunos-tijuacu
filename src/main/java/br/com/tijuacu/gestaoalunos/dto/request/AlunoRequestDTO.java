package br.com.tijuacu.gestaoalunos.dto.request;

import br.com.tijuacu.gestaoalunos.model.enums.Sexo;
import br.com.tijuacu.gestaoalunos.model.enums.TipoAee;
import br.com.tijuacu.gestaoalunos.model.enums.TransporteEscolar;
import br.com.tijuacu.gestaoalunos.validation.CPFValido;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Past;

import java.time.LocalDate;

public record AlunoRequestDTO(
        @NotBlank String nomeCompleto,
        @NotNull @Past LocalDate dataNascimento,
        String rg,
        @NotBlank @CPFValido String cpf,
        Sexo sexo,
        String corRaca,
        String cartaoSus,
        TransporteEscolar transporteEscolar,
        String nomePai,
        String nomeMae,
        TipoAee tipoAee,
        @Valid @NotNull EnderecoRequestDTO endereco
) {}