package com.feverdunk.site.models;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.util.List;

@Document(collection = "jogadores")
@Data
public class Jogador {

    @Id
    private String id;

    @Field("jogador_nome")
    @NotBlank
    private String nome;

    @Field("jogador_posicao")
    @NotBlank
    private String posicao;

    @Field("jogador_altura")
    @Min(1)
    private int altura;

    @Field("idade")
    @Min(1)
    private int idade;

    @Field("valor")
    @Min(0)
    private int valor;

    @Field("time_real")
    @NotBlank
    private String timeReal;

    @Field("pontucao")
    @Min(0)
    private int pontuacao;

    @Field("contrato")
    private List<Contrato> contratos;

    @Field("desempenho")
    private List<Desempenho> desempenhos;
}
