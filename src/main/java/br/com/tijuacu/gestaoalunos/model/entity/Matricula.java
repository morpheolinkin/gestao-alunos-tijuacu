package br.com.tijuacu.gestaoalunos.model.entity;

import br.com.tijuacu.gestaoalunos.model.audit.EntidadeAuditavel;
import br.com.tijuacu.gestaoalunos.model.enums.SituacaoMatricula;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "matricula")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Matricula extends EntidadeAuditavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer numero;
    private Integer anoLetivo;
    private LocalDate dataMatricula;

    @Enumerated(EnumType.STRING)
    private SituacaoMatricula situacao;

    private LocalDate dataSaida;     // novo
    private String motivoSaida;      // novo

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "aluno_id", nullable = false)
    private Aluno aluno;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "turma_id", nullable = false)
    private Turma turma;

    @Column(nullable = false)
    private Boolean ativa;
}