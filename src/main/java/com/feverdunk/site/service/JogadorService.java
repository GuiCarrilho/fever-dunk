package com.feverdunk.site.service;

import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Jogador;
import com.feverdunk.site.repository.JogadorRepository;
import jakarta.persistence.EntityNotFoundException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class JogadorService {
    private JogadorRepository jogadorRepository;

    @Autowired
    public JogadorService(JogadorRepository jogadorRepository){
        this.jogadorRepository = jogadorRepository;
    }


    public List<Jogador> getJogador(){
        return jogadorRepository.findAll();
    }


    public Jogador findById(Long id){
        Optional<Jogador> jogador = jogadorRepository.findById(id);

        return jogador.orElseThrow(() -> new ObjectNotFoundException("Jogador com id: {" + id + "} não foi encontrado"));
    }

    public List<Jogador> findAllByTimeId(Long timeId){
        return jogadorRepository.findAllByTimeId(timeId);
    }

    public Jogador create(Jogador jogador){
        jogador.setId(null);

        return jogadorRepository.save(jogador);
    }

    public Jogador update(Jogador jogadorNovo){
        return jogadorRepository.save(jogadorNovo);
    }

    public void delete(Long id){
        jogadorRepository.delete(findById(id));
    }


}
