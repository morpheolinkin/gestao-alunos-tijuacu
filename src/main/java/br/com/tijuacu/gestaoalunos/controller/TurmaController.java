package br.com.tijuacu.gestaoalunos.controller;

import br.com.tijuacu.gestaoalunos.dto.request.TurmaRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.TurmaResponseDTO;
import br.com.tijuacu.gestaoalunos.service.TurmaService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/api/turmas")
@RequiredArgsConstructor
@Tag(name = "Turmas", description = "Operações de CRUD de turmas")
public class TurmaController {

    private final TurmaService turmaService;

    @GetMapping
    @Operation(summary = "Lista todas as turmas")
    public ResponseEntity<List<TurmaResponseDTO>> listarTodas() {
        return ResponseEntity.ok(turmaService.listarTodas());
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca uma turma por ID")
    public ResponseEntity<TurmaResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(turmaService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cria uma nova turma")
    public ResponseEntity<TurmaResponseDTO> criar(@Valid @RequestBody TurmaRequestDTO dto) {
        TurmaResponseDTO salva = turmaService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salva);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza uma turma existente")
    public ResponseEntity<TurmaResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody TurmaRequestDTO dto
    ) {
        return ResponseEntity.ok(turmaService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Encerra uma turma (ativa = false)")
    public ResponseEntity<Void> encerrar(@PathVariable Long id) {
        turmaService.encerrarTurma(id);
        return ResponseEntity.noContent().build();
    }
}