package com.feverdunk.site.service;

import com.feverdunk.site.models.Liga;
import com.feverdunk.site.models.compositeIDs.ContratoId;
import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Contrato;
import com.feverdunk.site.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;

    @Autowired
    public ContratoService(ContratoRepository contratoRepository){this.contratoRepository = contratoRepository;}

    public List<Contrato> getContrato() {
        return contratoRepository.findAll();
    }

    public Contrato findById(ContratoId id) {
        Optional<Contrato> contrato = contratoRepository.findById(id);

        return contrato.orElseThrow(() -> new ObjectNotFoundException("Contrato com id: {" + id + "} não foi encontrado"));
    }

    public List<Contrato> findAllByTimeId(Long timeId){
        return contratoRepository.findAllByTimeId(timeId);
    }

    @Transactional
    public Contrato create(Contrato contrato) {
        contrato.setId(null);

        return contratoRepository.save(contrato);
    }

    @Transactional
    public Contrato update(Contrato contratoNovo) {
        Contrato contrato = findById(contratoNovo.getId());

        contrato.setTime(contratoNovo.getTime());
        contrato.setJogador(contratoNovo.getJogador());
        contrato.setAdquiridoEm(contratoNovo.getAdquiridoEm());
        contrato.setVendidoEm(contratoNovo.getVendidoEm());

        return contratoRepository.save(contrato);
    }

    public void delete(ContratoId id) {
        Contrato contrato = findById(id);
        LocalDateTime data = LocalDateTime.now();
        contrato.setVendidoEm(data);
    }
}
