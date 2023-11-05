package com.feverdunk.site.models;

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
    @ManyToOne
    @JoinColumn(name = "jogador_id")
    private Jogador jogador;

    @ManyToOne
    @JoinColumn(name = "time_id")
    private Time time;

    @Column(name = "adquirido_em")
    @NotNull
    private LocalDateTime adquiridoEm;

    @Column(name = "vendido_em")
    private LocalDateTime vendidoEm;
}
