package com.feverdunk.site.repository;

import com.feverdunk.site.models.Manager;
import org.springframework.stereotype.Repository;
import org.springframework.data.mongodb.repository.MongoRepository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends MongoRepository<Manager, String> {
    public Optional<Manager> findByEmail(String email);
}
