package br.com.tijuacu.gestaoalunos.service;

import br.com.tijuacu.gestaoalunos.dto.request.TurmaRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.TurmaResponseDTO;
import br.com.tijuacu.gestaoalunos.exception.TurmaNaoEncontradaException;
import br.com.tijuacu.gestaoalunos.mapper.TurmaMapper;
import br.com.tijuacu.gestaoalunos.model.entity.Turma;
import br.com.tijuacu.gestaoalunos.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class TurmaService {

    private final TurmaRepository turmaRepository;
    private final TurmaMapper turmaMapper;

    public List<TurmaResponseDTO> listarTodas() {
        return turmaRepository.findAll().stream()
                .map(turmaMapper::toResponseDTO)
                .toList();
    }

    public TurmaResponseDTO buscarPorId(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new TurmaNaoEncontradaException(id));
        return turmaMapper.toResponseDTO(turma);
    }

    public TurmaResponseDTO criar(TurmaRequestDTO dto) {
        Turma turma = turmaMapper.toEntity(dto);
        Turma salva = turmaRepository.save(turma);
        return turmaMapper.toResponseDTO(salva);
    }

    public TurmaResponseDTO atualizar(Long id, TurmaRequestDTO dto) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new TurmaNaoEncontradaException(id));

        turmaMapper.updateEntityFromDTO(dto, turma);
        Turma atualizada = turmaRepository.save(turma);

        return turmaMapper.toResponseDTO(atualizada);
    }

    public void encerrarTurma(Long id) {
        Turma turma = turmaRepository.findById(id)
                .orElseThrow(() -> new TurmaNaoEncontradaException(id));

        turma.setAtiva(false);
        turmaRepository.save(turma);
    }
}