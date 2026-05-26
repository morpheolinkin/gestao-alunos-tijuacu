package br.com.tijuacu.gestaoalunos.service;

import br.com.tijuacu.gestaoalunos.dto.request.MatriculaRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.MatriculaResponseDTO;
import br.com.tijuacu.gestaoalunos.exception.AlunoNaoEncontradoException;
import br.com.tijuacu.gestaoalunos.exception.MatriculaAtivaJaExistenteException;
import br.com.tijuacu.gestaoalunos.exception.MatriculaNaoEncontradaException;
import br.com.tijuacu.gestaoalunos.exception.TurmaNaoEncontradaException;
import br.com.tijuacu.gestaoalunos.mapper.MatriculaMapper;
import br.com.tijuacu.gestaoalunos.model.entity.Aluno;
import br.com.tijuacu.gestaoalunos.model.entity.Matricula;
import br.com.tijuacu.gestaoalunos.model.entity.Turma;
import br.com.tijuacu.gestaoalunos.model.enums.SituacaoMatricula;
import br.com.tijuacu.gestaoalunos.repository.AlunoRepository;
import br.com.tijuacu.gestaoalunos.repository.MatriculaRepository;
import br.com.tijuacu.gestaoalunos.repository.TurmaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@RequiredArgsConstructor
public class MatriculaService {

    private final MatriculaRepository matriculaRepository;
    private final AlunoRepository alunoRepository;
    private final TurmaRepository turmaRepository;
    private final MatriculaMapper matriculaMapper;

    public List<MatriculaResponseDTO> listarTodas() {
        return matriculaRepository.findAll().stream()
                .map(matriculaMapper::toResponseDTO)
                .toList();
    }

    public List<MatriculaResponseDTO> listarPorTurma(Long turmaId) {
        return matriculaRepository.findByTurmaIdAndAtivaTrue(turmaId)
                .stream()
                .map(matriculaMapper::toResponseDTO)
                .toList();
    }

    public List<MatriculaResponseDTO> listarHistoricoPorAluno(Long alunoId) {
        return matriculaRepository.findByAlunoIdOrderByAnoLetivoAsc(alunoId)
                .stream()
                .map(matriculaMapper::toResponseDTO)
                .toList();
    }

    public List<MatriculaResponseDTO> listarPorAnoESituacao(Integer anoLetivo, SituacaoMatricula situacao) {
        return matriculaRepository.findByAnoLetivoAndSituacaoAndAtivaTrue(anoLetivo, situacao)
                .stream()
                .map(matriculaMapper::toResponseDTO)
                .toList();
    }

    public MatriculaResponseDTO buscarPorId(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new MatriculaNaoEncontradaException(id));

        return matriculaMapper.toResponseDTO(matricula);
    }

    public MatriculaResponseDTO criar(MatriculaRequestDTO dto) {
        // Busca aluno e turma
        Aluno aluno = alunoRepository.findById(dto.alunoId())
                .orElseThrow(() -> new AlunoNaoEncontradoException(dto.alunoId()));

        Turma turma = turmaRepository.findById(dto.turmaId())
                .orElseThrow(() -> new TurmaNaoEncontradaException(dto.turmaId()));

        // Regra: não pode haver matrícula MATRICULADO ativa nesse ano
        if (dto.situacao() == SituacaoMatricula.MATRICULADO) {
            matriculaRepository.findByAlunoIdAndAnoLetivoAndSituacaoAndAtivaTrue(
                            dto.alunoId(),
                            dto.anoLetivo(),
                            SituacaoMatricula.MATRICULADO
                    )
                    .ifPresent(m -> {
                        throw new MatriculaAtivaJaExistenteException(dto.alunoId(), dto.anoLetivo());
                    });
        }

        Matricula matricula = matriculaMapper.toEntity(dto, aluno, turma);
        Matricula salva = matriculaRepository.save(matricula);

        return matriculaMapper.toResponseDTO(salva);
    }

    public MatriculaResponseDTO atualizar(Long id, MatriculaRequestDTO dto) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new MatriculaNaoEncontradaException(id));

        // Por simplicidade: não vamos revalidar toda a regra aqui agora;
        // em um cenário real, você decidira se pode mudar ano/turma/situação livremente.

        matricula.setNumero(dto.numero());
        matricula.setAnoLetivo(dto.anoLetivo());
        matricula.setDataMatricula(dto.dataMatricula());
        matricula.setSituacao(dto.situacao());
        // aluno e turma normalmente não mudam; se quiser mudar, precisa mais regra.

        Matricula atualizada = matriculaRepository.save(matricula);
        return matriculaMapper.toResponseDTO(atualizada);
    }

    public void cancelar(Long id) {
        Matricula matricula = matriculaRepository.findById(id)
                .orElseThrow(() -> new MatriculaNaoEncontradaException(id));

        matricula.setAtiva(false);
        matriculaRepository.save(matricula);
    }
}