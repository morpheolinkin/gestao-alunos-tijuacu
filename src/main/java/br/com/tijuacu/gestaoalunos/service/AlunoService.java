package br.com.tijuacu.gestaoalunos.service;

import br.com.tijuacu.gestaoalunos.dto.request.AlunoRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.AlunoResponseDTO;
import br.com.tijuacu.gestaoalunos.dto.response.PaginatedResponse;
import br.com.tijuacu.gestaoalunos.exception.AlunoNaoEncontradoException;
import br.com.tijuacu.gestaoalunos.mapper.AlunoMapper;
import br.com.tijuacu.gestaoalunos.model.entity.Aluno;
import br.com.tijuacu.gestaoalunos.model.enums.Sexo;
import br.com.tijuacu.gestaoalunos.model.enums.TipoAee;
import br.com.tijuacu.gestaoalunos.model.enums.TransporteEscolar;
import br.com.tijuacu.gestaoalunos.repository.AlunoRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AlunoService {

    private final AlunoRepository alunoRepository;
    private final AlunoMapper alunoMapper;

    public PaginatedResponse<AlunoResponseDTO> listar(
            String nome,
            Sexo sexo,
            TransporteEscolar transporteEscolar,
            TipoAee tipoAee,
            Pageable pageable
    ) {
        Page<Aluno> page;

        if (nome != null && !nome.isBlank()) {
            page = alunoRepository.findByAtivoTrueAndNomeCompletoContainingIgnoreCase(nome, pageable);
        } else if (sexo != null) {
            page = alunoRepository.findByAtivoTrueAndSexo(sexo, pageable);
        } else if (transporteEscolar != null) {
            page = alunoRepository.findByAtivoTrueAndTransporteEscolar(transporteEscolar, pageable);
        } else if (tipoAee != null) {
            page = alunoRepository.findByAtivoTrueAndTipoAee(tipoAee, pageable);
        } else {
            page = alunoRepository.findByAtivoTrue(pageable);
        }

        var content = page.getContent()
                .stream()
                .map(alunoMapper::toResponseDTO)
                .toList();

        return new PaginatedResponse<>(
                content,
                page.getNumber(),
                page.getSize(),
                page.getTotalElements(),
                page.getTotalPages()
        );
    }

    public AlunoResponseDTO buscarPorId(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException(id));

        return alunoMapper.toResponseDTO(aluno);
    }

    public AlunoResponseDTO criar(AlunoRequestDTO dto) {
        Aluno aluno = alunoMapper.toEntity(dto);
        Aluno salvo = alunoRepository.save(aluno);
        return alunoMapper.toResponseDTO(salvo);
    }

    public AlunoResponseDTO atualizar(Long id, AlunoRequestDTO dto) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException(id));

        alunoMapper.updateEntityFromDTO(dto, aluno);
        Aluno atualizado = alunoRepository.save(aluno);

        return alunoMapper.toResponseDTO(atualizado);
    }

    public void deletar(Long id) {
        Aluno aluno = alunoRepository.findById(id)
                .orElseThrow(() -> new AlunoNaoEncontradoException(id));

        aluno.setAtivo(false);
        alunoRepository.save(aluno);
    }
}