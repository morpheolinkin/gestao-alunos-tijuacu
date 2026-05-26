package br.com.tijuacu.gestaoalunos.controller;

import br.com.tijuacu.gestaoalunos.dto.response.DashboardResumoDTO;
import br.com.tijuacu.gestaoalunos.service.DashboardService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/dashboard")
@RequiredArgsConstructor
@Tag(name = "Dashboard", description = "Resumo de indicadores da gestão escolar")
public class DashboardController {

    private final DashboardService dashboardService;

    @GetMapping
    @Operation(summary = "Retorna indicadores consolidados para o dashboard")
    public ResponseEntity<DashboardResumoDTO> getResumo(
            @RequestParam(required = false) Integer anoLetivo
    ) {
        DashboardResumoDTO resumo = dashboardService.getResumo(anoLetivo);
        return ResponseEntity.ok(resumo);
    }
}