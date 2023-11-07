package com.feverdunk.site.repository;

import com.feverdunk.site.models.Liga;
import com.feverdunk.site.models.Manager;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface LigaRepository extends JpaRepository<Liga, Long> {
    @Query(value = "SELECT l.* FROM ligas l " +
            "JOIN participacoes p ON (l.liga_id = p.participacao_id_liga) " +
            "JOIN times t ON (p.time_id_participacao = t.time_id) " +
            "WHERE t.time_id = ?1", nativeQuery = true)
    public List<Liga> findAllByTimeId(Long id);

    public List<Liga> findAllByManagerId(Long id);
}
