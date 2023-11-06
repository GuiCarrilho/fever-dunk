package com.feverdunk.site.repository;

import com.feverdunk.site.compositeIDs.ParticipacaoId;
import com.feverdunk.site.models.Participacao;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ParticipacaoRepository extends JpaRepository<Participacao, ParticipacaoId> {
}
