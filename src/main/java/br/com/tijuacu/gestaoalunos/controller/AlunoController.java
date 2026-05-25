package br.com.tijuacu.gestaoalunos.controller;

import br.com.tijuacu.gestaoalunos.dto.request.AlunoRequestDTO;
import br.com.tijuacu.gestaoalunos.dto.response.AlunoResponseDTO;
import br.com.tijuacu.gestaoalunos.dto.response.PaginatedResponse;
import br.com.tijuacu.gestaoalunos.model.enums.Sexo;
import br.com.tijuacu.gestaoalunos.model.enums.TipoAee;
import br.com.tijuacu.gestaoalunos.model.enums.TransporteEscolar;
import br.com.tijuacu.gestaoalunos.service.AlunoService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springdoc.core.annotations.ParameterObject;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/alunos")
@RequiredArgsConstructor
@Tag(name = "Alunos", description = "Operações de CRUD de alunos")
public class AlunoController {

    private final AlunoService alunoService;

    @Operation(summary = "Lista todos os alunos com filtros opcionais",
            description = "Retorna uma lista paginada de alunos. Permite filtrar por nome, sexo, transporte escolar e tipo de AEE.")
    @GetMapping
    public ResponseEntity<PaginatedResponse<AlunoResponseDTO>> listar(
            @RequestParam(required = false) String nome,
            @RequestParam(required = false) Sexo sexo,
            @RequestParam(required = false) TransporteEscolar transporteEscolar,
            @RequestParam(required = false) TipoAee tipoAee,
            @ParameterObject Pageable pageable
    ) {
        Pageable pageablePadrao = pageable;
        if (pageable.getSort().isUnsorted()) {
            pageablePadrao = PageRequest.of(
                    pageable.getPageNumber(),
                    pageable.getPageSize(),
                    Sort.by("nomeCompleto").ascending()
            );
        }

        return ResponseEntity.ok(
                alunoService.listar(nome, sexo, transporteEscolar, tipoAee, pageablePadrao)
        );
    }

    @GetMapping("/{id}")
    @Operation(summary = "Busca um aluno por ID")
    public ResponseEntity<AlunoResponseDTO> buscarPorId(@PathVariable Long id) {
        return ResponseEntity.ok(alunoService.buscarPorId(id));
    }

    @PostMapping
    @Operation(summary = "Cria um novo aluno")
    public ResponseEntity<AlunoResponseDTO> criar(@Valid @RequestBody AlunoRequestDTO dto) {
        AlunoResponseDTO salvo = alunoService.criar(dto);
        return ResponseEntity.status(HttpStatus.CREATED).body(salvo);
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualiza um aluno existente")
    public ResponseEntity<AlunoResponseDTO> atualizar(
            @PathVariable Long id,
            @Valid @RequestBody AlunoRequestDTO dto
    ) {
        return ResponseEntity.ok(alunoService.atualizar(id, dto));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remove um aluno")
    public ResponseEntity<Void> deletar(@PathVariable Long id) {
        alunoService.deletar(id);
        return ResponseEntity.noContent().build();
    }
}