package com.feverdunk.site.repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.feverdunk.site.models.Jogador;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {
}
