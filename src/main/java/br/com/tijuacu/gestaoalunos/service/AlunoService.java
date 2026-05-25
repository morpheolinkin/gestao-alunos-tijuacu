package br.com.tijuacu.gestaoalunos.service;

import br.com.tijuacu.gestaoalunos.dto.request.AlunoRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.AlunoResponseDTO;
import br.com.tijuacu.gestaoalunos.mapper.AlunoMapper;
import br.com.tijuacu.gestaoalunos.model.entity.Aluno;
import br.com.tijuacu.gestaoalunos.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final AlunoMapper alunoMapper;

    public List<AlunoResponseDTO> listarTodos() {
        return alunoRepository.findAll()
                .stream()
                .map(alunoMapper::toResponseDTO)
                .toList();
    }

    public AlunoResponseDTO buscarPorId(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        return alunoMapper.toResponseDTO(aluno);
    }

    public AlunoResponseDTO criar(AlunoRequestDTO dto) {
        Aluno aluno = alunoMapper.toEntity(dto);
        Aluno salvo = alunoRepository.save(aluno);
        return alunoMapper.toResponseDTO(salvo);
    }

    public AlunoResponseDTO atualizar(Long id, AlunoRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        alunoMapper.updateEntityFromDTO(dto, aluno);
        Aluno atualizado = alunoRepository.save(aluno);

        return alunoMapper.toResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("Aluno não encontrado"));

        alunoRepository.delete(aluno);
    }
}