package com.feverdunk.site.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.feverdunk.site.models.Jogador;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.core.MongoTemplate;
import org.springframework.data.mongodb.core.query.Criteria;

@Repository
public interface JogadorRepository extends MongoRepository<Jogador, String> {

    public List<Jogador> findAllByContratos_Time_id(String timeid);

}