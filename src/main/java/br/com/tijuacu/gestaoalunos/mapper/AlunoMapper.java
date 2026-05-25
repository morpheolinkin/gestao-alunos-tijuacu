package br.com.tijuacu.gestaoalunos.mapper;

import br.com.tijuacu.gestaoalunos.dto.request.AlunoRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.request.EnderecoRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.AlunoResponseDTO;
import br.com.tijuacu.gestaoalunos.dto.response.EnderecoResponseDTO;
import br.com.tijuacu.gestaoalunos.model.entity.Aluno;
import br.com.tijuacu.gestaoalunos.model.entity.Endereco;
import org.springframework.stereotype.Component;

import java.time.LocalDate;
import java.time.Period;

@Component
public class AlunoMapper {

    public Aluno toEntity(AlunoRequestDTO dto) {
        EnderecoRequestDTO end = dto.endereco();

        Endereco endereco = Endereco.builder()
                .logradouro(end.logradouro())
                .numero(end.numero())
                .bairro(end.bairro())
                .distrito(end.distrito())
                .cidade(end.cidade())
                .cep(end.cep())
                .zona(end.zona())
                .build();

        return Aluno.builder()
                .nomeCompleto(dto.nomeCompleto())
                .dataNascimento(dto.dataNascimento())
                .rg(dto.rg())
                .cpf(dto.cpf())
                .sexo(dto.sexo())
                .corRaca(dto.corRaca())
                .cartaoSus(dto.cartaoSus())
                .transporteEscolar(dto.transporteEscolar())
                .nomePai(dto.nomePai())
                .nomeMae(dto.nomeMae())
                .tipoAee(dto.tipoAee())
                .endereco(endereco)
                .ativo(true)
                .build();
    }

    private Integer calcularIdade(LocalDate dataNascimento) {
        if (dataNascimento == null) return null;
        return Period.between(dataNascimento, LocalDate.now()).getYears();
    }

    public AlunoResponseDTO toResponseDTO(Aluno entity) {
        Endereco end = entity.getEndereco();

        EnderecoResponseDTO enderecoDTO = new EnderecoResponseDTO(
                end.getId(),
                end.getLogradouro(),
                end.getNumero(),
                end.getBairro(),
                end.getDistrito(),
                end.getCidade(),
                end.getCep(),
                end.getZona()
        );

        Integer idade = calcularIdade(entity.getDataNascimento());

        return new AlunoResponseDTO(
                entity.getId(),
                entity.getNomeCompleto(),
                entity.getDataNascimento(),
                idade,
                entity.getRg(),
                entity.getCpf(),
                entity.getSexo(),
                entity.getCorRaca(),
                entity.getCartaoSus(),
                entity.getTransporteEscolar(),
                entity.getNomePai(),
                entity.getNomeMae(),
                entity.getTipoAee(),
                enderecoDTO
        );
    }

    public void updateEntityFromDTO(AlunoRequestDTO dto, Aluno entity) {
        entity.setNomeCompleto(dto.nomeCompleto());
        entity.setDataNascimento(dto.dataNascimento());
        entity.setRg(dto.rg());
        entity.setCpf(dto.cpf());
        entity.setSexo(dto.sexo());
        entity.setCorRaca(dto.corRaca());
        entity.setCartaoSus(dto.cartaoSus());
        entity.setTransporteEscolar(dto.transporteEscolar());
        entity.setNomePai(dto.nomePai());
        entity.setNomeMae(dto.nomeMae());
        entity.setTipoAee(dto.tipoAee());

        EnderecoRequestDTO end = dto.endereco();
        Endereco endereco = entity.getEndereco();
        endereco.setLogradouro(end.logradouro());
        endereco.setNumero(end.numero());
        endereco.setBairro(end.bairro());
        endereco.setDistrito(end.distrito());
        endereco.setCidade(end.cidade());
        endereco.setCep(end.cep());
        endereco.setZona(end.zona());
    }
}