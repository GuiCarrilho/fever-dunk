package com.feverdunk.site.service;

import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Liga;
import com.feverdunk.site.models.Manager;
import com.feverdunk.site.repository.LigaRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class LigaService {
    private final LigaRepository ligaRepository;

    @Autowired
    public LigaService(LigaRepository ligaRepository){ this.ligaRepository = ligaRepository; }
    public List<Liga> getLiga() {
        return ligaRepository.findAll();
    }

    public Liga findById(Long id) {
        Optional<Liga> liga = ligaRepository.findById(id);

        return liga.orElseThrow(() -> new ObjectNotFoundException("Liga com id: {" + id + "} não foi encontrado"));
    }

    public Liga create(Liga liga) {
        liga.setId(null);

        return ligaRepository.save(liga);
    }

    public Liga update(Liga ligaNova) {
        Liga liga = findById(ligaNova.getId());

        liga.setDescricao(ligaNova.getDescricao());
        liga.setParticipacao(ligaNova.getParticipacao());
        liga.setSenha(ligaNova.getSenha());

        return ligaRepository.save(liga);
    }

    public void delete(Long id) {
        Liga liga = findById(id);

        ligaRepository.delete(liga);
    }
}
