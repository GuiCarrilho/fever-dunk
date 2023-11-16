package com.feverdunk.site.repository;

import com.feverdunk.site.models.Liga;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface LigaRepository extends MongoRepository<Liga, String> {

    List<Liga> findAllByParticipacao_Time_id(String id);
    List<Liga> findAllByManagerId(String id);
}