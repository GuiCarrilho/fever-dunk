package com.feverdunk.site.controller;

import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Desempenho;
import com.feverdunk.site.service.DesempenhoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/desempenho")
public class DesempenhoController {

    private DesempenhoService desempenhoService;

    @Autowired
    public DesempenhoController(DesempenhoService desempenhoService) {this.desempenhoService = desempenhoService; }

    @GetMapping
    public ResponseEntity<List<Desempenho>> getDesmpenho(){
        List<Desempenho> desempenhos = desempenhoService.getDesempenho();
        return ResponseEntity.ok(desempenhos);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Desempenho> getById(@PathVariable Long id){
        Desempenho desempenho = desempenhoService.findById(id);
        return ResponseEntity.ok(desempenho);
    }

    @PreAuthorize("ROLE_ADMIN")
    @PostMapping
    public ResponseEntity<Desempenho> post(@RequestBody @Validated Desempenho desempenho) { return criarDesempenho(desempenho); }

    @PreAuthorize("ROLE_ADMIN")
    @PutMapping
    public ResponseEntity<Desempenho> put(@RequestBody @Validated Desempenho desempenho){
        try{
            desempenhoService.findById(desempenho.getId());

            Desempenho desempenhoAtualizado = desempenhoService.update(desempenho);

            return ResponseEntity.ok(desempenhoAtualizado);
        }catch (ObjectNotFoundException ex){
            return criarDesempenho(desempenho);
        }
    }

    @PreAuthorize("ROLE_ADMIN")
    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        desempenhoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Desempenho> criarDesempenho(Desempenho desempenho){
        Desempenho desempenhoCriado = desempenhoService.create(desempenho);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}").buildAndExpand(desempenhoCriado.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

}
