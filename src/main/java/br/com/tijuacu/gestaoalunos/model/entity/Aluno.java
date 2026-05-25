package br.com.tijuacu.gestaoalunos.model.entity;

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

    private String sexo; // M ou F

    private String corRaca;

    private String cartaoSus;

    private Boolean transporteEscolar; // true = SIM, false = NAO

    private String nomePai;

    private String nomeMae;

    // AEE pode ser um campo de texto com siglas ou, no futuro, uma enum/lista
    private String tipoAee;
    // Ex.: "DI", "DA", "DV", "PC", "TEA" ou combinação (pensamos melhor depois)

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "endereco_id")
    private Endereco endereco;
}