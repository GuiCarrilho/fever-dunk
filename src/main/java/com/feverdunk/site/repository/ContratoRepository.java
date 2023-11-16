package com.feverdunk.site.repository;

import com.feverdunk.site.models.Contrato;
import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface ContratoRepository extends MongoRepository<Contrato, String> {
    List<Contrato> findAllByTimeId(String id);
    List<Contrato> findAllByJogadorId(String id);
}