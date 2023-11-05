package com.feverdunk.site.models;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "Time")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
public class Time {

    @Id
    @OneToOne
    @JoinColumn(name = "manager_id")
    private Manager manager;

    @Column(name = "nome")
    private String nome;

    @Column(name = "pontuação")
    private int pontuacao;

    @OneToMany(mappedBy = "time")
    private List<Contrato> contratos;

}
