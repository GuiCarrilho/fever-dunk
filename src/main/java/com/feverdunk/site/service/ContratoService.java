package com.feverdunk.site.service;

import com.feverdunk.site.models.Contrato;
import com.feverdunk.site.repository.ContratoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;

    @Autowired
    public ContratoService(ContratoRepository contratoRepository){this.contratoRepository = contratoRepository;}

    public List<Contrato> getContrato() {
        return contratoRepository.findAll();
    }
}
