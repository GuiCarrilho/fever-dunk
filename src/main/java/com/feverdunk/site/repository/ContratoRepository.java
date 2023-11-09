package com.feverdunk.site.repository;

import com.feverdunk.site.models.compositeIDs.ContratoId;
import com.feverdunk.site.models.Contrato;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface ContratoRepository extends JpaRepository<Contrato, ContratoId> {
    @Query(value = "SELECT c.* FROM contratos c " +
            "where c.vendido_em IS NULL", nativeQuery = true)
    public List<Contrato> findAllByTimeId(Long id);

    public List<Contrato> findAllByJogadorId(Long id);
}
