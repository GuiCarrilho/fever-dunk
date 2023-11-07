package com.feverdunk.site.repository;

import com.feverdunk.site.models.compositeIDs.ContratoId;
import com.feverdunk.site.models.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, ContratoId> {
    public List<Contrato> findAllByTimeId(Long id);
}
