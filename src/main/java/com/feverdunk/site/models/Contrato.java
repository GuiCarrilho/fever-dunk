package com.feverdunk.site.models;

import com.feverdunk.site.models.compositeIDs.ContratoId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.*;
import org.springframework.data.mongodb.core.mapping.DBRef;
import org.springframework.data.mongodb.core.mapping.Document;
import org.springframework.data.mongodb.core.mapping.Field;

import java.time.LocalDateTime;
import java.util.Collection;

@Document(collection = "contratos")
@Data
public class Contrato {

    @Id
    private String id;

    @Field("jogador_id_contrato")
    @DBRef
    private Jogador jogador;

    @Field("time_id_contrato")
    @DBRef
    private Time time;

    @Field("adquirido_em")
    @NotNull
    private LocalDateTime adquiridoEm;

    @Field("vendido_em")
    private LocalDateTime vendidoEm;
}
