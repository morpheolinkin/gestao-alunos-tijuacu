package br.com.tijuacu.gestaoalunos.controller;

import br.com.tijuacu.gestaoalunos.dto.request.MatriculaRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.request.MatriculaSituacaoRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.MatriculaResponseDTO;
import br.com.tijuacu.gestaoalunos.model.enums.SituacaoMatricula;
import br.com.tijuacu.gestaoalunos.service.MatriculaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.List;

@RestController
@RequestMapping("/api/matriculas")
@RequiredArgsConstructor
@Tag(name = "Matrículas", description = "Operações de matrícula de alunos em turmas")
public class MatriculaController {

    private final MatriculaService matriculaService;

    @GetMapping
    @Operation(summary = "Lista todas as matrículas")
    public ResponseEntity<List<MatriculaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(matriculaService.listarTodas());
    }

    @GetMapping("/turma/{turmaId}")
    @Operation(summary = "Lista matrículas ativas de uma turma")
    public ResponseEntity<List<MatriculaResponseDTO>> listarPorTurma(@PathVariable Long turmaId) {
        return ResponseEntity.ok(matriculaService.listarPorTurma(turmaId));
    }

    @GetMapping("/aluno/{alunoId}")
    @Operation(summary = "Lista histórico de matrículas de um aluno")
    public ResponseEntity<List<MatriculaResponseDTO>> listarHistoricoPorAluno(@PathVariable Long alunoId) {
        return ResponseEntity.ok(matriculaService.listarHistoricoPorAluno(alunoId));
    }

    @GetMapping("/filtro")
    @Operation(summary = "Lista matrículas por ano letivo e situação")
    public ResponseEntity<List<MatriculaResponseDTO>> listarPorAnoESituacao(
            @RequestParam Integer anoLetivo,
            @RequestParam SituacaoMatricula situacao
    ) {
        return ResponseEntity.ok(matriculaService.listarPorAnoESituacao(anoLetivo, situacao));
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma matrícula por ID")
    public ResponseEntity<MatriculaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(matriculaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cria uma nova matrícula")
    public ResponseEntity<MatriculaResponseDTO> criar(@Valid @RequestBody MatriculaRequestDTO dto) {
        MatriculaResponseDTO salva = matriculaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma matrícula existente")
    public ResponseEntity<MatriculaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody MatriculaRequestDTO dto
    ) {
        return ResponseEntity.ok(matriculaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Cancela uma matrícula (ativa = false)")
    public ResponseEntity<Void> cancelar(@PathVariable Long id) {
        matriculaService.cancelar(id);
        return ResponseEntity.noContent().build();
    }

    @PatchMapping("/{id}/evadir")
    @Operation(summary = "Marca uma matrícula como EVADIDO")
    public ResponseEntity<MatriculaResponseDTO> marcarComoEvadido(
            @PathVariable Long id,
            @Valid @RequestBody MatriculaSituacaoRequestDTO dto
    ) {
        return ResponseEntity.ok(matriculaService.marcarComoEvadido(id, dto));
    }

    @PatchMapping("/{id}/transferir")
    @Operation(summary = "Marca uma matrícula como TRANSFERIDO")
    public ResponseEntity<MatriculaResponseDTO> marcarComoTransferido(
            @PathVariable Long id,
            @Valid @RequestBody MatriculaSituacaoRequestDTO dto
    ) {
        return ResponseEntity.ok(matriculaService.marcarComoTransferido(id, dto));
    }

    @PatchMapping("/{id}/aprovar")
    @Operation(summary = "Marca uma matrícula como APROVADO")
    public ResponseEntity<MatriculaResponseDTO> marcarComoAprovado(
            @PathVariable Long id,
            @Valid @RequestBody MatriculaSituacaoRequestDTO dto
    ) {
        return ResponseEntity.ok(matriculaService.marcarComoAprovado(id, dto));
    }
}