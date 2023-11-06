package com.feverdunk.site.controller;

import com.feverdunk.site.compositeIDs.ParticipacaoId;
import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Participacao;
import com.feverdunk.site.service.ParticipacaoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/participacao")
public class ParticipacaoController {

    private ParticipacaoService participacaoService;

    @Autowired
    public ParticipacaoController(ParticipacaoService participacaoService){ this.participacaoService = participacaoService; }

    @GetMapping
    public ResponseEntity<List<Participacao>> getParticipacao(){
        List<Participacao> participacaos = participacaoService.getParticipao();

        return ResponseEntity.ok(participacaos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Participacao> getById(@PathVariable ParticipacaoId id){
        Participacao participacao = participacaoService.findById(id);

        return ResponseEntity.ok(participacao);
    }

    @PostMapping
    public ResponseEntity<Participacao> post(@RequestBody @Validated Participacao participacao){ return criarParticipacao(participacao); }

    @PutMapping
    public ResponseEntity<Participacao> put(@RequestBody @Validated Participacao participacao){
        try{
            participacaoService.findById(participacao.getId());

            Participacao participacaoAtualizada = participacaoService.update(participacao);

            return ResponseEntity.ok(participacaoAtualizada);
        }catch (ObjectNotFoundException ex){
            return criarParticipacao(participacao);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable ParticipacaoId id){
        participacaoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Participacao> criarParticipacao(Participacao participacao){
        Participacao participacaoCriada = participacaoService.create(participacao);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}").buildAndExpand(participacaoCriada.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
