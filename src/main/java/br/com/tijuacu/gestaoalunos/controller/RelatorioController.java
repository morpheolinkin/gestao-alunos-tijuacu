package br.com.tijuacu.gestaoalunos.controller;

import br.com.tijuacu.gestaoalunos.dto.response.AlunoResponseDTO;
import br.com.tijuacu.gestaoalunos.dto.response.MatriculaResponseDTO;
import br.com.tijuacu.gestaoalunos.dto.response.PaginatedResponse;
import br.com.tijuacu.gestaoalunos.model.enums.SituacaoMatricula;
import br.com.tijuacu.gestaoalunos.service.AlunoService;
import br.com.tijuacu.gestaoalunos.service.MatriculaService;
import br.com.tijuacu.gestaoalunos.service.RelatorioService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.core.io.InputStreamResource;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.io.ByteArrayInputStream;
import java.util.List;

@RestController
@RequestMapping("/api/relatorios")
@RequiredArgsConstructor
@Tag(name = "Relatórios", description = "Endpoints para exportação de dados em Excel e PDF")
public class RelatorioController {

    private final RelatorioService relatorioService;
    private final AlunoService alunoService;
    private final MatriculaService matriculaService;

    @GetMapping("/excel/alunos")
    @Operation(summary = "Exporta a listagem de alunos ativos para formato Excel (.xlsx)")
    public ResponseEntity<InputStreamResource> baixarExcelAlunos() {
        // Busca todos os alunos cadastrados sem limitação de paginação
        PaginatedResponse<AlunoResponseDTO> resultado = alunoService.listar(
                null, null, null, null, Pageable.unpaged()
        );
        List<AlunoResponseDTO> alunos = resultado.content();

        ByteArrayInputStream stream = relatorioService.gerarExcelAlunos(alunos);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=relatorio_alunos.xlsx");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.parseMediaType("application/vnd.openxmlformats-officedocument.spreadsheetml.sheet"))
                .body(new InputStreamResource(stream));
    }

    @GetMapping("/pdf/matriculas")
    @Operation(summary = "Exporta o histórico de matrículas filtrado por ano para PDF")
    public ResponseEntity<InputStreamResource> baixarPdfMatriculas(
            @RequestParam(required = false) Integer anoLetivo,
            @RequestParam(required = false) SituacaoMatricula situacao
    ) {
        List<MatriculaResponseDTO> matriculas;

        if (anoLetivo != null && situacao != null) {
            matriculas = matriculaService.listarPorAnoESituacao(anoLetivo, situacao);
        } else {
            matriculas = matriculaService.listarTodas();
        }

        ByteArrayInputStream stream = relatorioService.gerarPdfMatriculas(matriculas, anoLetivo);

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Disposition", "attachment; filename=relatorio_matriculas.pdf");

        return ResponseEntity.ok()
                .headers(headers)
                .contentType(MediaType.APPLICATION_PDF)
                .body(new InputStreamResource(stream));
    }
}