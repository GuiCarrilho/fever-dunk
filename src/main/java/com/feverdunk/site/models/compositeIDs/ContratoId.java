package com.feverdunk.site.models.compositeIDs;


import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ContratoId implements Serializable {

    @Column(name = "jogador_id_contrato")
    private String jogadorId;

    @Column(name = "time_id_contrato")
    private String timeId;
}
