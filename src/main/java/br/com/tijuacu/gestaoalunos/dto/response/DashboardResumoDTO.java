package br.com.tijuacu.gestaoalunos.dto.response;

import java.util.Map;

public record DashboardResumoDTO(
        long totalAlunosAtivos,
        long totalMatriculasNoAnoLetivo,
        Map<String, Long> alunosPorSexo,
        Map<String, Long> alunosPorTipoAee,
        Map<String, Long> alunosPorTransporte,
        Map<String, Long> matriculasPorSituacaoAno,
        Map<String, Long> evasoesPorMes,
        Map<String, Long> transferenciasPorMes,
        Map<String, Long> matriculasPorSituacaoGeral,
        Map<Long, Long> alunosPorTurmaNoAno
) {}