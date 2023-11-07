package com.feverdunk.site.controller;

import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Jogador;
import com.feverdunk.site.service.JogadorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/jogador")
public class JogadorController {
    private JogadorService jogadorService;


    @Autowired
    public JogadorController(JogadorService jogadorService){
        this.jogadorService = jogadorService;
    }

    @GetMapping
    public ResponseEntity<List<Jogador>> getJogador(){
        List<Jogador> jogadores = jogadorService.getJogador();
        return ResponseEntity.ok(jogadores);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Jogador> getById(@PathVariable Long id){
        Jogador jogador = jogadorService.findById(id);

        return ResponseEntity.ok(jogador);
    }

    @GetMapping("/time/{id}")
    public ResponseEntity<List<Jogador>> getJogadorByTimeId(@PathVariable Long id){
        List<Jogador> jogadores = jogadorService.findAllByTimeId(id);
        return ResponseEntity.ok(jogadores);
    }

    @PreAuthorize("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Jogador> post(@RequestBody @Validated Jogador jogador){
        return criarJogador(jogador);
    }

    @PreAuthorize("ROLE_ADMIN")
    @PutMapping
    public ResponseEntity<Jogador> put(@RequestBody @Validated Jogador jogador){
        try{
            jogadorService.findById(jogador.getId());


            Jogador jogadorAtualizado = jogadorService.update(jogador);

            return ResponseEntity.ok(jogadorAtualizado);
        }catch (ObjectNotFoundException ex){
            return criarJogador(jogador);
        }
    }

    @PreAuthorize("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        jogadorService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Jogador> criarJogador(Jogador jogador) {
        Jogador jogadorCriado = jogadorService.create(jogador);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}").buildAndExpand(jogadorCriado.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
