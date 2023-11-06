package com.feverdunk.site.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "times")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Time {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "time_id")
    private Long id;

    @Column(name = "time_nome")
    private String nome;

    @Column(name = "time_pontuacao")
    private int pontuacao;

    @JsonIgnore
    @OneToMany(mappedBy = "time")
    private List<Contrato> contratos;

}
