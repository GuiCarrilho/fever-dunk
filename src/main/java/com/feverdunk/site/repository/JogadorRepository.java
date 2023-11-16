package com.feverdunk.site.repository;

import java.util.List;
import org.springframework.data.mongodb.repository.MongoRepository;
import com.feverdunk.site.models.Jogador;
import org.springframework.stereotype.Repository;

@Repository
public interface JogadorRepository extends MongoRepository<Jogador, String> {

    List<Jogador> findAllByContratos_Time_id(String timeid);
}