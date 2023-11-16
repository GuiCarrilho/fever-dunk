package com.feverdunk.site.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDate;

@Document(collection = "desempenhos")
@Data
public class Desempenho {

    @Id
    private String id;

    @Field("desempenho_data")
    @NotNull
    private LocalDate data;

    @Field("pontos")
    @Min(value = 0)
    private int pontos;

    @Field("rebotes")
    @NotNull
    @Min(value = 0)
    private int rebotes;

    @Field("assistencias")
    @NotNull
    @Min(value = 0)
    private int assistencias;

    @Field("min jogados")
    @NotNull
    @Min(value = 0)
    private int minJogados;

    @Field("desempenho_jogador_id")
    @DBRef
    private Jogador jogador;
}