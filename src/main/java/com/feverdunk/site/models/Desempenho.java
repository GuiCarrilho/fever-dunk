package com.feverdunk.site.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;

import java.time.LocalDate;
import java.util.List;

@Entity
@Table(name = "desempenhos")
public class Desempenho {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "data")
    @NotNull
    private LocalDate data;

    @Column(name = "pontos")
    @NotNull
    private int pontos;

    @Column(name = "rebotes")
    @NotNull
    private int rebotes;

    @Column(name = "assisntencias")
    @NotNull
    private int assistencias;

    @Column(name = "min_jogados")
    @NotNull
    private int minJogados;

    @ManyToOne
    @JoinColumn(name = "jogador_id")
    private Jogador jogador;
}
