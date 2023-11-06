package com.feverdunk.site.repository;

import com.feverdunk.site.models.Time;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface TimeRepository extends JpaRepository<Time, Long> {

    @Query(value = "select t.* from participacoes p " +
            "join ligas l on l.liga_id  = p.participacao_id_liga " +
            "join times t on t.time_id = p.participacao_id_time " +
            "where l.liga_id = ?1", nativeQuery = true)
    public List<Time> findTimesFromLiga(Long ligaId);
}
