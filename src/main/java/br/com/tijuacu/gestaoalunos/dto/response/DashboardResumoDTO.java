package br.com.tijuacu.gestaoalunos.dto.response;

import java.util.Map;

public record DashboardResumoDTO(
        long totalAlunosAtivos,
        Map<String, Long> alunosPorSexo,
        Map<String, Long> alunosPorTipoAee,
        Map<String, Long> alunosPorTransporte,
        Map<String, Long> matriculasPorSituacaoAno,       // por situação no ano (MATRICULADO, EVADIDO, etc.)
        Map<String, Long> evasoesPorMes,                  // chave tipo "01", "02", ...
        Map<String, Long> transferenciasPorMes,           // idem
        Map<String, Long> matrículasPorSituacaoGeral      // total histórico por situação (aprovado, conservado, etc.)
) {}