package com.feverdunk.site.models;

import com.feverdunk.site.compositeIDs.ContratoId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@Entity
@Getter
@Setter
@AllArgsConstructor
@NoArgsConstructor
@Table(name = "contratos")
public class Contrato {

    @EmbeddedId
    private ContratoId id;
    
    @ManyToOne
    @MapsId("jogadorId")
    @JoinColumn(name = "jogador_id_contrato")
    private Jogador jogador;

    @ManyToOne
    @MapsId("timeId")
    @JoinColumn(name = "time_id_contrato",  referencedColumnName = "time_id")
    private Time time;

    @Column(name = "adquirido_em")
    @NotNull
    private LocalDateTime adquiridoEm;

    @Column(name = "vendido_em")
    private LocalDateTime vendidoEm;
}
