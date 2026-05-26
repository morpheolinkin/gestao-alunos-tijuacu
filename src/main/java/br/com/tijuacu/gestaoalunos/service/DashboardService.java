package br.com.tijuacu.gestaoalunos.service;

import br.com.tijuacu.gestaoalunos.dto.response.DashboardResumoDTO;
import br.com.tijuacu.gestaoalunos.model.enums.Sexo;
import br.com.tijuacu.gestaoalunos.model.enums.SituacaoMatricula;
import br.com.tijuacu.gestaoalunos.model.enums.TipoAee;
import br.com.tijuacu.gestaoalunos.model.enums.TransporteEscolar;
import br.com.tijuacu.gestaoalunos.repository.AlunoRepository;
import br.com.tijuacu.gestaoalunos.repository.MatriculaRepository;
import br.com.tijuacu.gestaoalunos.repository.TurmaRepository;
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
    private final TurmaRepository turmaRepository;

    public DashboardResumoDTO getResumo(Integer anoLetivo) {
        long totalAlunosAtivos = alunoRepository.countByAtivoTrue();

        Map<String, Long> alunosPorSexo = new LinkedHashMap<>();
        for (Sexo sexo : Sexo.values()) {
            alunosPorSexo.put(sexo.name(), alunoRepository.countByAtivoTrueAndSexo(sexo));
        }

        Map<String, Long> alunosPorTransporte = new LinkedHashMap<>();
        for (TransporteEscolar t : TransporteEscolar.values()) {
            alunosPorTransporte.put(t.name(), alunoRepository.countByAtivoTrueAndTransporteEscolar(t));
        }

        Map<String, Long> alunosPorTipoAee = new LinkedHashMap<>();
        for (TipoAee tipo : TipoAee.values()) {
            alunosPorTipoAee.put(tipo.name(), alunoRepository.countByAtivoTrueAndTipoAee(tipo));
        }

        // Situações no ano (MATRICULADO, EVADIDO, TRANSFERIDO, APROVADO, CONSERVADO, FALECIDO)
        Map<String, Long> matriculasPorSituacaoAno = new LinkedHashMap<>();
        if (anoLetivo != null) {
            for (SituacaoMatricula sit : SituacaoMatricula.values()) {
                long count = matriculaRepository.countByAnoLetivoAndSituacao(anoLetivo, sit);
                matriculasPorSituacaoAno.put(sit.name(), count);
            }
        }

        // Situações gerais (histórico)
        Map<String, Long> matriculasPorSituacaoGeral = new LinkedHashMap<>();
        for (SituacaoMatricula sit : SituacaoMatricula.values()) {
            long count = matriculaRepository.countBySituacao(sit);
            matriculasPorSituacaoGeral.put(sit.name(), count);
        }

        // Evasões por mês no ano
        Map<String, Long> evasoesPorMes = new HashMap<>();
        Map<String, Long> transferenciasPorMes = new HashMap<>();

        if (anoLetivo != null) {
            // EVADIDO
            List<Object[]> evasoes = matriculaRepository.contarPorMesEAnoAndSituacao(
                    anoLetivo, SituacaoMatricula.EVADIDO
            );
            for (Object[] row : evasoes) {
                Integer mes = ((Number) row[0]).intValue();
                Long total = ((Number) row[1]).longValue();
                String mesStr = String.format("%02d", mes); // "01", "02", ...
                evasoesPorMes.put(mesStr, total);
            }

            // TRANSFERIDO
            List<Object[]> transf = matriculaRepository.contarPorMesEAnoAndSituacao(
                    anoLetivo, SituacaoMatricula.TRANSFERIDO
            );
            for (Object[] row : transf) {
                Integer mes = ((Number) row[0]).intValue();
                Long total = ((Number) row[1]).longValue();
                String mesStr = String.format("%02d", mes);
                transferenciasPorMes.put(mesStr, total);
            }
        }

        return new DashboardResumoDTO(
                totalAlunosAtivos,
                alunosPorSexo,
                alunosPorTipoAee,
                alunosPorTransporte,
                matriculasPorSituacaoAno,
                evasoesPorMes,
                transferenciasPorMes,
                matriculasPorSituacaoGeral
        );
    }
}