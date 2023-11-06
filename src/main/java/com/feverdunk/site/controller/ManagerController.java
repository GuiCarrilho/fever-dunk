package com.feverdunk.site.controller;

import com.feverdunk.site.dto.manager.ManagerInDTO;
import com.feverdunk.site.dto.manager.ManagerOutDTO;
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
import java.util.stream.Collectors;

@RestController
@RequestMapping("/manager")
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService){ this.managerService = managerService; }

    @GetMapping
    public ResponseEntity<List<ManagerOutDTO>> getManager(){
        List<Manager> managers = managerService.getManager();
        List<ManagerOutDTO> managersDto = managers.stream()
                .map(this::entityToOut).collect(Collectors.toList());

        return ResponseEntity.ok(managersDto);
    }

    @GetMapping("/{id}")
    public ResponseEntity<ManagerOutDTO> getById(@PathVariable Long id){
        Manager manager = managerService.findById(id);
        ManagerOutDTO dto = entityToOut(manager);

        return ResponseEntity.ok(dto);
    }

    @PostMapping
    public ResponseEntity<ManagerOutDTO> post(@RequestBody @Validated ManagerInDTO dto){
        Manager manager = inToEntity(dto);
        return criarManager(manager);
    }

    @PutMapping
    public ResponseEntity<ManagerOutDTO> put(@RequestBody @Validated ManagerInDTO dto){
        Manager manager = inToEntity(dto);
        if(manager.getId() != null) {
            try {
                managerService.findById(dto.getId());
                ManagerOutDTO outDTO = entityToOut(managerService.update(manager));
                return ResponseEntity.ok(outDTO);

            } catch (ObjectNotFoundException ex) {
                return criarManager(manager);
            }
        }
        else {
            return criarManager(manager);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        managerService.delete(id);

        return ResponseEntity.noContent().build();
    }

    public ResponseEntity<ManagerOutDTO> criarManager(@RequestBody @Validated Manager manager){
        Manager managerCriado = managerService.create(manager);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}").buildAndExpand(managerCriado.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    private Manager inToEntity(ManagerInDTO dto){
        Manager manager = new Manager();
        manager.setNome(dto.getNome());
        manager.setSenha(dto.getSenha());
        manager.setEmail(dto.getEmail());
        try{
            manager.setId(dto.getId());
        } catch (NullPointerException ex){

        }

        return manager;
    }

    private ManagerOutDTO entityToOut(Manager manager){
        ManagerOutDTO dto = new ManagerOutDTO();
        dto.setId(manager.getId());
        dto.setNome(manager.getNome());
        dto.setEmail(manager.getEmail());
        dto.setPremium(manager.isPremium());

        try {
            dto.setTimeId(manager.getTime().getId());
        }catch (NullPointerException ex){
            dto.setTimeId(-1L);
        }

        return dto;
    }

}
