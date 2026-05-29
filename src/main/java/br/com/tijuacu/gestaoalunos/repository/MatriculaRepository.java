package br.com.tijuacu.gestaoalunos.repository;

import br.com.tijuacu.gestaoalunos.model.entity.Matricula;
import br.com.tijuacu.gestaoalunos.model.enums.SituacaoMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    // Regra de negócio já usada
    java.util.Optional<Matricula> findByAlunoIdAndAnoLetivoAndSituacaoAndAtivaTrue(
            Long alunoId,
            Integer anoLetivo,
            SituacaoMatricula situacao
    );

    // Consultas para refinamento
    // Sobrescrevendo o findById padrão para trazer aluno e turma juntos
    @Query("SELECT m FROM Matricula m JOIN FETCH m.aluno JOIN FETCH m.turma WHERE m.id = :id")
    Optional<Matricula> findDetalhadoById(@Param("id") Long id);

    // Sobrescrevendo o findAll
    @Query("SELECT m FROM Matricula m JOIN FETCH m.aluno JOIN FETCH m.turma")
    List<Matricula> findDetalhadoAll();

    @Query("SELECT m FROM Matricula m JOIN FETCH m.aluno JOIN FETCH m.turma WHERE m.turma.id = :turmaId AND m.ativa = true")
    List<Matricula> findByTurmaIdAndAtivaTrue(@Param("turmaId") Long turmaId);

    @Query("SELECT m FROM Matricula m JOIN FETCH m.aluno JOIN FETCH m.turma WHERE m.aluno.id = :alunoId ORDER BY m.anoLetivo ASC")
    List<Matricula> findByAlunoIdOrderByAnoLetivoAsc(@Param("alunoId") Long alunoId);

    @Query("SELECT m FROM Matricula m JOIN FETCH m.aluno JOIN FETCH m.turma WHERE m.anoLetivo = :anoLetivo AND m.situacao = :situacao AND m.ativa = true")
    List<Matricula> findByAnoLetivoAndSituacaoAndAtivaTrue(@Param("anoLetivo") Integer anoLetivo, @Param("situacao") SituacaoMatricula situacao);

    List<Matricula> findByTurmaIdAndSituacao(Long turmaId, SituacaoMatricula situacao);

    // Evasões por mês (no ano letivo)
    long countByAnoLetivo(Integer anoLetivo);

    long countByAnoLetivoAndSituacao(Integer anoLetivo, SituacaoMatricula situacao);

    long countBySituacao(SituacaoMatricula situacao);

    // Adicione no MatriculaRepository.java
    @Query("SELECT m.situacao, COUNT(m) FROM Matricula m WHERE m.anoLetivo = :ano GROUP BY m.situacao")
    List<Object[]> countMatriculasPorSituacaoAnoAgrupado(@Param("ano") Integer anoLetivo);

    @Query("SELECT m.situacao, COUNT(m) FROM Matricula m GROUP BY m.situacao")
    List<Object[]> countMatriculasPorSituacaoGeralAgrupado();

    @Query("""
           SELECT MONTH(m.dataSaida) AS mes, COUNT(m)
           FROM Matricula m
           WHERE m.anoLetivo = :ano
             AND m.situacao = :situacao
             AND m.dataSaida IS NOT NULL
           GROUP BY MONTH(m.dataSaida)
           ORDER BY MONTH(m.dataSaida)
           """)
    List<Object[]> contarPorMesEAnoAndSituacao(
            @Param("ano") Integer ano,
            @Param("situacao") SituacaoMatricula situacao
    );

    @Query("""
           SELECT m.turma.id, COUNT(m)
           FROM Matricula m
           WHERE m.anoLetivo = :ano AND m.ativa = true
           GROUP BY m.turma.id
           """)
    List<Object[]> countAlunosPorTurmaNoAno(@Param("ano") Integer ano);
}