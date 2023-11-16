package com.feverdunk.site.repository;

import com.feverdunk.site.models.Time;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.List;
import java.util.Optional;

public interface TimeRepository extends MongoRepository<Time, String> {
    public List<Time> findAllByParticipacoes_Liga_id(String ligaId);
}