package com.feverdunk.site.models;

import com.feverdunk.site.compositeIDs.ParticipacaoId;
import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;

@AllArgsConstructor
@NoArgsConstructor
@Getter
@Setter
@Entity
@Table(name = "participacoes")
public class Participacao {

    @EmbeddedId
    private ParticipacaoId id;

    @ManyToOne
    @MapsId("ligaId")
    @JoinColumn(name = "participacao_id_liga")
    private Liga liga;

    @ManyToOne
    @MapsId("timeId")
    @JoinColumn(name = "participacao_id_time", referencedColumnName = "time_id")
    private Time time;

    @Column(name = "participacao_data")
    @NotNull
    private LocalDateTime data;

    @Column(name = "ate")
    private LocalDateTime ate;
}
