package com.feverdunk.site.models;

import jakarta.persistence.*;
import java.util.List;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.springframework.beans.factory.annotation.Autowired;

@Entity
@Table(name = "Managers")
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor

public class Manager {

    public Manager(String nome, String email, String senha, int dinheiro, boolean premium) {
        this.nome = nome;
        this.email = email;
        this.senha = senha;
        this.dinheiro = dinheiro;
        this.premium = premium;
    }

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "nome")
    @NotBlank
    private String nome;

    @Column(name = "email")
    @NotBlank
    private String email;

    @Column(name = "senha")
    @NotBlank
    private String senha;

    @Column(name = "dinheiro")
    @NotNull
    private int dinheiro;

    @Column(name = "premium")
    @NotNull
    private boolean premium;

    @OneToOne(mappedBy = "manager")
    @JoinColumn(name = "time_id")
    private Time time;
}
