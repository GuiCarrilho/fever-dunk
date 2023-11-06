package com.feverdunk.site.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.util.List;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "ligas")
public class Liga {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "liga_id")
    private Long id;

    @Column(name = "descricao")
    @NotBlank
    private String descricao;

    @JsonIgnore
    @Column(name = "liga_senha")
    @NotBlank
    private String senha;

    @JsonIgnore
    @OneToMany(mappedBy = "liga")
    private List<Participacao> participacao;
}
