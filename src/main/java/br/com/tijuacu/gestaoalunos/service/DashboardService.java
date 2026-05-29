package br.com.tijuacu.gestaoalunos.service;

import br.com.tijuacu.gestaoalunos.dto.response.DashboardResumoDTO;
import br.com.tijuacu.gestaoalunos.model.enums.SituacaoMatricula;
import br.com.tijuacu.gestaoalunos.repository.AlunoRepository;
import br.com.tijuacu.gestaoalunos.repository.MatriculaRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;

@Service
@RequiredArgsConstructor
public class DashboardService {

    private final AlunoRepository alunoRepository;
    private final MatriculaRepository matriculaRepository;

    public DashboardResumoDTO getResumo(Integer anoLetivo) {
        long totalAlunosAtivos = alunoRepository.countByAtivoTrue();

        long totalMatriculasNoAnoLetivo = 0L;
        if (anoLetivo != null) {
            totalMatriculasNoAnoLetivo = matriculaRepository.countByAnoLetivo(anoLetivo);
        }

        // --- Processamento Otimizado com GROUP BY ---
        Map<String, Long> alunosPorSexo = converterParaMapString(alunoRepository.countAlunosPorSexoAgrupado());
        Map<String, Long> alunosPorTransporte = converterParaMapString(alunoRepository.countAlunosPorTransporteAgrupado());
        Map<String, Long> alunosPorTipoAee = converterParaMapString(alunoRepository.countAlunosPorTipoAeeAgrupado());

        Map<String, Long> matriculasPorSituacaoGeral = converterParaMapString(matriculaRepository.countMatriculasPorSituacaoGeralAgrupado());

        Map<String, Long> matriculasPorSituacaoAno = new LinkedHashMap<>();
        Map<String, Long> evasoesPorMes = new HashMap<>();
        Map<String, Long> transferenciasPorMes = new HashMap<>();
        Map<Long, Long> alunosPorTurmaNoAno = new LinkedHashMap<>();

        if (anoLetivo != null) {
            matriculasPorSituacaoAno = converterParaMapString(matriculaRepository.countMatriculasPorSituacaoAnoAgrupado(anoLetivo));

            evasoesPorMes = converterMesParaMap(matriculaRepository.contarPorMesEAnoAndSituacao(anoLetivo, SituacaoMatricula.EVADIDO));
            transferenciasPorMes = converterMesParaMap(matriculaRepository.contarPorMesEAnoAndSituacao(anoLetivo, SituacaoMatricula.TRANSFERIDO));

            List<Object[]> rowsTurmas = matriculaRepository.countAlunosPorTurmaNoAno(anoLetivo);
            for (Object[] row : rowsTurmas) {
                Long turmaId = ((Number) row[0]).longValue();
                Long total = ((Number) row[1]).longValue();
                alunosPorTurmaNoAno.put(turmaId, total);
            }
        }

        return new DashboardResumoDTO(
                totalAlunosAtivos,
                totalMatriculasNoAnoLetivo,
                alunosPorSexo,
                alunosPorTipoAee,
                alunosPorTransporte,
                matriculasPorSituacaoAno,
                evasoesPorMes,
                transferenciasPorMes,
                matriculasPorSituacaoGeral,
                alunosPorTurmaNoAno
        );
    }

    // Métodos auxiliares para converter as respostas de banco de dados em Maps
    private Map<String, Long> converterParaMapString(List<Object[]> resultados) {
        Map<String, Long> mapa = new LinkedHashMap<>();
        for (Object[] row : resultados) {
            if (row[0] != null) {
                mapa.put(row[0].toString(), ((Number) row[1]).longValue());
            }
        }
        return mapa;
    }

    private Map<String, Long> converterMesParaMap(List<Object[]> resultados) {
        Map<String, Long> mapa = new HashMap<>();
        for (Object[] row : resultados) {
            if (row[0] != null) {
                Integer mes = ((Number) row[0]).intValue();
                String mesStr = String.format("%02d", mes);
                mapa.put(mesStr, ((Number) row[1]).longValue());
            }
        }
        return mapa;
    }
}