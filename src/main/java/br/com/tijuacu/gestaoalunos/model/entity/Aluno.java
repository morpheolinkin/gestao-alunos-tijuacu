package br.com.tijuacu.gestaoalunos.model.entity;

import br.com.tijuacu.gestaoalunos.model.enums.Sexo;
import br.com.tijuacu.gestaoalunos.model.enums.TipoAee;
import br.com.tijuacu.gestaoalunos.model.enums.TransporteEscolar;
import jakarta.persistence.*;
import lombok.*;

import java.time.LocalDate;

@Entity
@Table(name = "aluno")
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class Aluno {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    private String nomeCompleto;

    private LocalDate dataNascimento;

    private String rg;

    @Column(nullable = false)
    private String cpf;

    @Enumerated(EnumType.STRING)
    private Sexo sexo; // MASCULINO / FEMININO

    private String corRaca;

    private String cartaoSus;

    @Enumerated(EnumType.STRING)
    private TransporteEscolar transporteEscolar; // SIM / NAO

    private String nomePai;

    private String nomeMae;

    @Enumerated(EnumType.STRING)
    private TipoAee tipoAee;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
}