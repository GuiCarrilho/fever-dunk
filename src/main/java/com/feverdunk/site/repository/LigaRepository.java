package com.feverdunk.site.repository;

import com.feverdunk.site.models.Liga;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

public interface LigaRepository extends JpaRepository<Liga, Long> {
}
