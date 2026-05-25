package br.com.tijuacu.gestaoalunos.repository;

import br.com.tijuacu.gestaoalunos.model.entity.Turma;
import org.springframework.data.jpa.repository.JpaRepository;

public interface TurmaRepository extends JpaRepository<Turma, Long> {
    // depois podemos ter filtros por anoLetivo, modalidade, turno etc.
}