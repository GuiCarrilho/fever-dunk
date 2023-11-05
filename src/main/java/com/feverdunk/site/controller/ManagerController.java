package com.feverdunk.site.controller;

import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Manager;
import com.feverdunk.site.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService){ this.managerService = managerService; }

    @GetMapping
    public List<Manager> getManager(){
        return managerService.getManager();
    }

    @GetMapping("/{id}")
    public Manager getById(Long id){
        return managerService.findById(id);
    }

    @PostMapping
    public ResponseEntity<Manager> post(@RequestBody @Validated Manager manager){
        return criarMenager(manager);
    }

    @PutMapping
    public ResponseEntity<Manager> put(@RequestBody @Validated Manager manager){
        try{
            managerService.findById(manager.getId());

            Manager managerAtualizado = managerService.update(manager);
            return ResponseEntity.ok(managerAtualizado);
        }catch (ObjectNotFoundException e){
            return criarMenager(manager);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Manager> delete(@PathVariable Long id){
        managerService.delete(id);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<Manager> criarMenager(@RequestBody @Validated Manager manager){
        Manager managerCriado = managerService.create(manager);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}").buildAndExpand(managerCriado.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }
}
