package com.feverdunk.site.models;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;

@Document(collection = "participacoes")
@Data
public class Participacao {

    @Id
    private String id;

    @Field("participacao_id_liga")
    @DBRef
    private Liga liga;

    @Field("participacao_id_time")
    @DBRef
    private Time time;

    @Field("participacao_data")
    @NotNull
    private LocalDateTime data;

    @Field("ate")
    private LocalDateTime ate;
}