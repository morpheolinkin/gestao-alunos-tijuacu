package br.com.tijuacu.gestaoalunos.mapper;

import br.com.tijuacu.gestaoalunos.dto.request.AlunoRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.request.EnderecoRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.AlunoResponseDTO;
import br.com.tijuacu.gestaoalunos.dto.response.EnderecoResponseDTO;
import br.com.tijuacu.gestaoalunos.model.entity.Aluno;
import br.com.tijuacu.gestaoalunos.model.entity.Endereco;
import org.springframework.stereotype.Component;

@Component
public class AlunoMapper {

    public Aluno toEntity(AlunoRequestDTO dto) {
        if (dto == null) return null;

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
                .cpf(dto.cpf())
                .sexo(dto.sexo())
                .corRaca(dto.corRaca())
                .aee(dto.aee())
                .endereco(endereco)
                .build();
    }

    public AlunoResponseDTO toResponseDTO(Aluno entity) {
        if (entity == null) return null;

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

        return new AlunoResponseDTO(
                entity.getId(),
                entity.getNomeCompleto(),
                entity.getDataNascimento(),
                entity.getCpf(),
                entity.getSexo(),
                entity.getCorRaca(),
                entity.getAee(),
                enderecoDTO
        );
    }

    public void updateEntityFromDTO(AlunoRequestDTO dto, Aluno entity) {
        entity.setNomeCompleto(dto.nomeCompleto());
        entity.setDataNascimento(dto.dataNascimento());
        entity.setCpf(dto.cpf());
        entity.setSexo(dto.sexo());
        entity.setCorRaca(dto.corRaca());
        entity.setAee(dto.aee());

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