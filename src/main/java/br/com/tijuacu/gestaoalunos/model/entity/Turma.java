package br.com.tijuacu.gestaoalunos.model.entity;

import br.com.tijuacu.gestaoalunos.model.audit.EntidadeAuditavel;
import br.com.tijuacu.gestaoalunos.model.enums.Modalidade;
import br.com.tijuacu.gestaoalunos.model.enums.Turno;
import jakarta.persistence.*;
import lombok.*;

@Entity
@Table(name = "turma")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Turma extends EntidadeAuditavel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private Integer anoLetivo;      // 2025, 2026...

    @Column(nullable = false)
    private String nome;            // "6º A", "7º B", "EJA Noturno"

    private String etapa;           // "6º ano", "7º ano", "EJA I", etc.

    @Enumerated(EnumType.STRING)
    private Turno turno;            // MANHA, TARDE, NOITE

    @Enumerated(EnumType.STRING)
    private Modalidade modalidade;  // REGULAR, EJA

    private Integer capacidadeMaxima;

    @Column(nullable = false)
    private Boolean ativa;
}