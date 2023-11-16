package com.feverdunk.site.repository;

import com.feverdunk.site.models.Time;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;

public interface TimeRepository extends MongoRepository<Time, String> {
    List<Time> findAllByParticipacoes_Liga_id(String ligaId);
}