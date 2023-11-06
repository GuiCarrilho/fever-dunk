package com.feverdunk.site.service;

import com.feverdunk.site.models.compositeIDs.ParticipacaoId;
import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Participacao;
import com.feverdunk.site.repository.ParticipacaoRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Service
public class ParticipacaoService {

    private ParticipacaoRepository participacaoRepository;

    @Autowired
    public ParticipacaoService(ParticipacaoRepository participacaoRepository){ this.participacaoRepository = participacaoRepository; }

    public List<Participacao> getParticipao(){ return  participacaoRepository.findAll(); }

    public Participacao findById(ParticipacaoId id){
        Optional<Participacao> participacao = participacaoRepository.findById(id);

        return participacao.orElseThrow(() -> new ObjectNotFoundException("Participação com id: {" + id + "} não foi encontrado"));
    }

    public Participacao create(Participacao participacao){
        participacao.setId(null);

        return participacaoRepository.save(participacao);
    }

    public Participacao update(Participacao participacaoNova) {
        Participacao participacao = findById(participacaoNova.getId());

        participacao.setData(participacaoNova.getData());
        participacao.setAte(participacaoNova.getAte());
        participacao.setTime(participacaoNova.getTime());
        participacao.setLiga(participacaoNova.getLiga());

        return participacaoRepository.save(participacaoNova);
    }

    public void delete(ParticipacaoId id){
        Participacao participacao = findById(id);
        LocalDateTime data = LocalDateTime.now();
        participacao.setAte(data);
    }
}
