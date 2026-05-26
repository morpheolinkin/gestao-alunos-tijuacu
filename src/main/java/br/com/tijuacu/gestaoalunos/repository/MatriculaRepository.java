package br.com.tijuacu.gestaoalunos.repository;

import br.com.tijuacu.gestaoalunos.model.entity.Matricula;
import br.com.tijuacu.gestaoalunos.model.enums.SituacaoMatricula;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

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
}