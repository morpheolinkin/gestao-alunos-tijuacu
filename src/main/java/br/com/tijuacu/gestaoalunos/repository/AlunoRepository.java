package br.com.tijuacu.gestaoalunos.repository;

import br.com.tijuacu.gestaoalunos.model.entity.Aluno;
import br.com.tijuacu.gestaoalunos.model.enums.Sexo;
import br.com.tijuacu.gestaoalunos.model.enums.TipoAee;
import br.com.tijuacu.gestaoalunos.model.enums.TransporteEscolar;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AlunoRepository extends JpaRepository<Aluno, Long> {
    Page<Aluno> findByAtivoTrue(Pageable pageable);

    Page<Aluno> findByAtivoTrueAndNomeCompletoContainingIgnoreCase(String nomeCompleto, Pageable pageable);

    Page<Aluno> findByAtivoTrueAndSexo(Sexo sexo, Pageable pageable);

    Page<Aluno> findByAtivoTrueAndTransporteEscolar(TransporteEscolar transporteEscolar, Pageable pageable);

    Page<Aluno> findByAtivoTrueAndTipoAee(TipoAee tipoAee, Pageable pageable);

    long countByAtivoTrue();

    long countByAtivoTrueAndSexo(Sexo sexo);

    long countByAtivoTrueAndTransporteEscolar(TransporteEscolar transporteEscolar);

    long countByAtivoTrueAndTipoAee(TipoAee tipoAee);
}
