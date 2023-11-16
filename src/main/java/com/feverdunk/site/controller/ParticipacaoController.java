package com.feverdunk.site.controller;

import com.feverdunk.site.models.compositeIDs.ParticipacaoId;
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
    public ResponseEntity<Participacao> getById(@PathVariable String id){
        Participacao participacao = participacaoService.findById(id);

        return ResponseEntity.ok(participacao);
    }

    @PostMapping("/{senha}")
    public ResponseEntity<Participacao> post(@RequestBody @Validated Participacao participacao,
                                             @PathVariable String senha){
        return criarParticipacao(participacao, senha);
    }

    @PutMapping("/{senha}")
    public ResponseEntity<Participacao> put(@RequestBody @Validated Participacao participacao,
                                            @PathVariable String senha){
        try{
            participacaoService.findById(participacao.getId());

            Participacao participacaoAtualizada = participacaoService.update(participacao);

            return ResponseEntity.ok(participacaoAtualizada);
        }catch (ObjectNotFoundException ex){
            return criarParticipacao(participacao, senha);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id){
        participacaoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Participacao> criarParticipacao(Participacao participacao, String senha){
        Participacao participacaoCriada = participacaoService.create(participacao, senha);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}").buildAndExpand(participacaoCriada.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
