package br.com.tijuacu.gestaoalunos.repository;

import br.com.tijuacu.gestaoalunos.model.entity.Matricula;
import br.com.tijuacu.gestaoalunos.model.enums.SituacaoMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.time.LocalDate;
import java.util.List;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    // Regra de negócio já usada
    java.util.Optional<Matricula> findByAlunoIdAndAnoLetivoAndSituacaoAndAtivaTrue(
            Long alunoId,
            Integer anoLetivo,
            SituacaoMatricula situacao
    );

    // Consultas para refinamento
    List<Matricula> findByTurmaIdAndAtivaTrue(Long turmaId);

    List<Matricula> findByAlunoIdOrderByAnoLetivoAsc(Long alunoId);

    List<Matricula> findByAnoLetivoAndSituacaoAndAtivaTrue(Integer anoLetivo, SituacaoMatricula situacao);

    List<Matricula> findByAnoLetivoAndSituacao(Integer anoLetivo, SituacaoMatricula situacao);

    List<Matricula> findByAnoLetivoAndSituacaoAndDataSaidaBetween(
            Integer anoLetivo,
            SituacaoMatricula situacao,
            LocalDate inicio,
            LocalDate fim
    );

    List<Matricula> findByTurmaIdAndSituacao(Long turmaId, SituacaoMatricula situacao);

    // Evasões por mês (no ano letivo)
    long countByAnoLetivo(Integer anoLetivo);

    long countByAnoLetivoAndSituacao(Integer anoLetivo, SituacaoMatricula situacao);

    long countBySituacao(SituacaoMatricula situacao);

    @Query("""
           SELECT EXTRACT(MONTH FROM m.dataSaida) AS mes, COUNT(m)
           FROM Matricula m
           WHERE m.anoLetivo = :ano
             AND m.situacao = :situacao
             AND m.dataSaida IS NOT NULL
           GROUP BY EXTRACT(MONTH FROM m.dataSaida)
           ORDER BY mes
           """)
    List<Object[]> contarPorMesEAnoAndSituacao(
            @Param("ano") Integer ano,
            @Param("situacao") SituacaoMatricula situacao
    );

    @Query("""
           SELECT m.turma.id, COUNT(m)
           FROM Matricula m
           WHERE m.anoLetivo = :ano
           GROUP BY m.turma.id
           """)
    List<Object[]> countAlunosPorTurmaNoAno(@Param("ano") Integer ano);
}