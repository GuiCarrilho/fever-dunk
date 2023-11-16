package com.feverdunk.site.repository;

import com.feverdunk.site.models.Desempenho;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface DesempenhoRepository extends MongoRepository<Desempenho, String> {
}