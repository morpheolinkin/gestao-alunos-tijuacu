package br.com.tijuacu.gestaoalunos.repository;

import br.com.tijuacu.gestaoalunos.model.entity.Matricula;
import br.com.tijuacu.gestaoalunos.model.enums.SituacaoMatricula;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface MatriculaRepository extends JpaRepository<Matricula, Long> {

    Optional<Matricula> findByAlunoIdAndAnoLetivoAndSituacaoAndAtivaTrue(
            Long alunoId,
            Integer anoLetivo,
            SituacaoMatricula situacao
    );
}