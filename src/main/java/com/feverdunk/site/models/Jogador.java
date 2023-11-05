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
    @Min(value = 1)
    private int altura;

    @Column(name = "idade")
    @NotNull
    @Min(value = 1)
    private int idade;

    @Column(name = "valor")
    @NotNull
    @Min(value = 0)
    private int valor;

    @Column(name = "time_real")
    @NotBlank
    private String timeReal;

    @Column(name = "pontuacao")
    @NotNull
    @Min(value = 0)
    private int pontuacao;

    @OneToMany(mappedBy = "jogador")
    private List<Desempenho> desempenhos;
}

