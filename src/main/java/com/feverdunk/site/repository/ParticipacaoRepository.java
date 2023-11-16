package com.feverdunk.site.repository;

import com.feverdunk.site.models.compositeIDs.ParticipacaoId;
import com.feverdunk.site.models.Participacao;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface ParticipacaoRepository extends MongoRepository<Participacao, String> {
}