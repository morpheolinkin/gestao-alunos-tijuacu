package br.com.tijuacu.gestaoalunos.mapper;

import br.com.tijuacu.gestaoalunos.dto.request.MatriculaRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.MatriculaResponseDTO;
import br.com.tijuacu.gestaoalunos.model.entity.Aluno;
import br.com.tijuacu.gestaoalunos.model.entity.Matricula;
import br.com.tijuacu.gestaoalunos.model.entity.Turma;
import org.springframework.stereotype.Component;

@Component
public class MatriculaMapper {

    public Matricula toEntity(
            MatriculaRequestDTO dto,
            Aluno aluno,
            Turma turma
    ) {
        return Matricula.builder()
                .numero(dto.numero())
                .anoLetivo(dto.anoLetivo())
                .dataMatricula(dto.dataMatricula())
                .situacao(dto.situacao())
                .dataSaida(dto.dataSaida())
                .motivoSaida(dto.motivoSaida())
                .aluno(aluno)
                .turma(turma)
                .ativa(true)
                .build();
    }

    public MatriculaResponseDTO toResponseDTO(Matricula entity) {
        return new MatriculaResponseDTO(
                entity.getId(),
                entity.getNumero(),
                entity.getAnoLetivo(),
                entity.getDataMatricula(),
                entity.getSituacao(),
                entity.getDataSaida(),
                entity.getMotivoSaida(),
                entity.getAluno().getId(),
                entity.getAluno().getNomeCompleto(),
                entity.getTurma().getId(),
                entity.getTurma().getNome()
        );
    }
}