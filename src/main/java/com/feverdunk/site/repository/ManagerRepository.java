package com.feverdunk.site.repository;

import com.feverdunk.site.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface ManagerRepository extends JpaRepository<Manager, Long> {
    public Optional<Manager> findByEmail(String email);
}
