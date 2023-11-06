package com.feverdunk.site.repository;

import com.feverdunk.site.compositeIDs.ContratoId;
import com.feverdunk.site.models.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;

public interface ContratoRepository extends JpaRepository<Contrato, ContratoId> {

}
