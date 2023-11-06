package com.feverdunk.site.compositeIDs;

import jakarta.persistence.Column;
import jakarta.persistence.Embeddable;
import lombok.Data;

import java.io.Serializable;

@Data
@Embeddable
public class ParticipacaoId implements Serializable {

    @Column(name = "participacao_id_liga")
    private Long ligaId;

    @Column(name = "participacao_id_time")
    private Long timeId;

}
