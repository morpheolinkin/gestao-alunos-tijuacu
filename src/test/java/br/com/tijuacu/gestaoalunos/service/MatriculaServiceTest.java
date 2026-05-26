package br.com.tijuacu.gestaoalunos.service;

import br.com.tijuacu.gestaoalunos.dto.request.MatriculaRequestDTO;
import br.com.tijuacu.gestaoalunos.exception.MatriculaAtivaJaExistenteException;
import br.com.tijuacu.gestaoalunos.model.entity.Aluno;
import br.com.tijuacu.gestaoalunos.model.entity.Matricula;
import br.com.tijuacu.gestaoalunos.model.entity.Turma;
import br.com.tijuacu.gestaoalunos.model.enums.SituacaoMatricula;
import br.com.tijuacu.gestaoalunos.repository.AlunoRepository;
import br.com.tijuacu.gestaoalunos.repository.MatriculaRepository;
import br.com.tijuacu.gestaoalunos.repository.TurmaRepository;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import br.com.tijuacu.gestaoalunos.dto.request.MatriculaSituacaoRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.MatriculaResponseDTO;

import java.time.LocalDate;

import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest
class MatriculaServiceTest {

    @Autowired
    private MatriculaService matriculaService;

    @Autowired
    private MatriculaRepository matriculaRepository;

    @Autowired
    private AlunoRepository alunoRepository;

    @Autowired
    private TurmaRepository turmaRepository;

    private Aluno aluno;
    private Turma turma;

    @BeforeEach
    void setup() {
        matriculaRepository.deleteAll();
        turmaRepository.deleteAll();
        alunoRepository.deleteAll();

        aluno = alunoRepository.save(
                Aluno.builder()
                        .nomeCompleto("Teste Aluno")
                        .cpf("12345678909") // se tiver validação, ajuste para um CPF válido
                        .ativo(true)
                        .build()
        );

        turma = turmaRepository.save(
                Turma.builder()
                        .anoLetivo(2026)
                        .nome("2º ano")
                        .etapa("EPJAI I")
                        .turno(br.com.tijuacu.gestaoalunos.model.enums.Turno.NOITE)
                        .modalidade(br.com.tijuacu.gestaoalunos.model.enums.Modalidade.EPJAI)
                        .capacidadeMaxima(30)
                        .ativa(true)
                        .build()
        );
    }

    @Test
    void naoDevePermitirDuasMatriculasAtivasNoMesmoAno() {
        // primeira matrícula MATRICULADO
        MatriculaRequestDTO primeira = new MatriculaRequestDTO(
                1,
                2026,
                LocalDate.of(2026, 2, 10),
                SituacaoMatricula.MATRICULADO,
                aluno.getId(),
                turma.getId(),
                null,
                null
        );

        matriculaService.criar(primeira);

        // segunda matrícula MATRICULADO no mesmo ano deve lançar exceção
        MatriculaRequestDTO segunda = new MatriculaRequestDTO(
                2,
                2026,
                LocalDate.of(2026, 3, 1),
                SituacaoMatricula.MATRICULADO,
                aluno.getId(),
                turma.getId(),
                null,
                null
        );

        assertThrows(
                MatriculaAtivaJaExistenteException.class,
                () -> matriculaService.criar(segunda)
        );
    }

    @Test
    void deveMarcarMatriculaComoEvadidoComDataEMotivoEInativar() {
        // cria matrícula MATRICULADO
        MatriculaRequestDTO request = new MatriculaRequestDTO(
                1,
                2026,
                LocalDate.of(2026, 2, 10),
                SituacaoMatricula.MATRICULADO,
                aluno.getId(),
                turma.getId(),
                null,
                null
        );

        MatriculaResponseDTO matriculaCriada = matriculaService.criar(request);

        MatriculaSituacaoRequestDTO situacaoDTO = new MatriculaSituacaoRequestDTO(
                LocalDate.of(2026, 5, 20),
                "Aluno parou de frequentar"
        );

        MatriculaResponseDTO evadida = matriculaService.marcarComoEvadido(
                matriculaCriada.id(),
                situacaoDTO
        );

        assertEquals(SituacaoMatricula.EVADIDO, evadida.situacao());
        assertEquals(LocalDate.of(2026, 5, 20), evadida.dataSaida());
        assertEquals("Aluno parou de frequentar", evadida.motivoSaida());

        Matricula entidade = matriculaRepository.findById(evadida.id()).orElseThrow();
        assertFalse(entidade.getAtiva());
    }

    @Test
    void deveMarcarMatriculaComoTransferidoComDataEMotivoEInativar() {
        MatriculaRequestDTO request = new MatriculaRequestDTO(
                1,
                2026,
                LocalDate.of(2026, 2, 10),
                SituacaoMatricula.MATRICULADO,
                aluno.getId(),
                turma.getId(),
                null,
                null
        );

        MatriculaResponseDTO matriculaCriada = matriculaService.criar(request);

        MatriculaSituacaoRequestDTO situacaoDTO = new MatriculaSituacaoRequestDTO(
                LocalDate.of(2026, 4, 15),
                "Transferência para escola da cidade vizinha"
        );

        MatriculaResponseDTO transferida = matriculaService.marcarComoTransferido(
                matriculaCriada.id(),
                situacaoDTO
        );

        assertEquals(SituacaoMatricula.TRANSFERIDO, transferida.situacao());
        assertEquals(LocalDate.of(2026, 4, 15), transferida.dataSaida());
        assertEquals("Transferência para escola da cidade vizinha", transferida.motivoSaida());

        Matricula entidade = matriculaRepository.findById(transferida.id()).orElseThrow();
        assertFalse(entidade.getAtiva());
    }
}