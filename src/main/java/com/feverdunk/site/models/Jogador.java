package com.feverdunk.site.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "jogadores")
public class Jogador {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    @NotBlank
    private String nome;

    @Column(name = "posicao")
    @NotBlank
    private String posicao;

    @Column(name = "altura")
    @NotNull
    private int altura;

    @Column(name = "idade")
    @NotNull
    private int idade;

    @Column(name = "valor")
    @NotNull
    private int valor;

    @Column(name = "time_real")
    @NotBlank
    private String timeReal;

    @Column(name = "pontuacao")
    @NotNull
    private int pontuacao;

    @OneToMany(mappedBy = "jogador")
    private List<Desempenho> desempenhos;

    @OneToMany(mappedBy = "jogador")
    private List<Contrato> contratos;

}


