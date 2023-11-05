package com.feverdunk.site.repository;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

import com.feverdunk.site.models.Jogador;
import org.springframework.data.jpa.repository.Query;

public interface JogadorRepository extends JpaRepository<Jogador, Long> {
    @Query(value = "SELECT j.* FROM jogadores j " +
            "JOIN contratos c ON (j.jogador_id = c.jogador_id_contrato) " +
            "JOIN times t ON (c.time_id_contrato = t.time_id) " +
            "WHERE t.time_id = ?1", nativeQuery = true)
    public List<Jogador> findAllByTimeId(Long id);

}
