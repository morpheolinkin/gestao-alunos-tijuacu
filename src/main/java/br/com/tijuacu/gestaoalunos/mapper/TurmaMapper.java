package br.com.tijuacu.gestaoalunos.mapper;

import br.com.tijuacu.gestaoalunos.dto.request.TurmaRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.TurmaResponseDTO;
import br.com.tijuacu.gestaoalunos.model.entity.Turma;
import org.springframework.stereotype.Component;

@Component
public class TurmaMapper {

    public Turma toEntity(TurmaRequestDTO dto) {
        if (dto == null) return null;

        return Turma.builder()
                .anoLetivo(dto.anoLetivo())
                .nome(dto.nome())
                .etapa(dto.etapa())
                .turno(dto.turno())
                .modalidade(dto.modalidade())
                .capacidadeMaxima(dto.capacidadeMaxima())
                .ativa(true)
                .build();
    }

    public TurmaResponseDTO toResponseDTO(Turma entity) {
        if (entity == null) return null;

        return new TurmaResponseDTO(
                entity.getId(),
                entity.getAnoLetivo(),
                entity.getNome(),
                entity.getEtapa(),
                entity.getTurno(),
                entity.getModalidade(),
                entity.getCapacidadeMaxima(),
                entity.getAtiva()
        );
    }

    public void updateEntityFromDTO(TurmaRequestDTO dto, Turma entity) {
        entity.setAnoLetivo(dto.anoLetivo());
        entity.setNome(dto.nome());
        entity.setEtapa(dto.etapa());
        entity.setTurno(dto.turno());
        entity.setModalidade(dto.modalidade());
        entity.setCapacidadeMaxima(dto.capacidadeMaxima());
        // ativa não muda aqui; outra operação pode encerrar a turma
    }
}