package com.feverdunk.site.controller;

import com.feverdunk.site.dto.time.TimeInDTO;
import com.feverdunk.site.dto.time.TimeOutDTO;
import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Time;
import com.feverdunk.site.service.TimeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;

@RestController
@RequestMapping("/time")
public class TimeController {

    private TimeService timeService;

    @Autowired
    public TimeController(TimeService timeService) {this.timeService = timeService;}

    @GetMapping
    public ResponseEntity<List<Time>> getTime(){
        List<Time> times = timeService.getTime();

        return ResponseEntity.ok(times);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Time> getById(@PathVariable Long id){
        Time time = timeService.findById(id);

        return ResponseEntity.ok(time);
    }

    @GetMapping("/liga/{ligaId}")
    public ResponseEntity<List<Time>> getTimeFromLiga(@PathVariable Long ligaId){
        List<Time> time = timeService.getTimeFromLiga(ligaId);

        return ResponseEntity.ok(time);
    }

    @PostMapping
    public ResponseEntity<Time> post(@RequestBody @Validated TimeInDTO dto) {
        Time time = inToEntity(dto);
        return criarTime(time);
    }

    @PutMapping
    public ResponseEntity<Time> put(@RequestBody @Validated TimeInDTO dto){
        Time time = inToEntity(dto);
        try{
            timeService.findById(time.getId());

            Time timeAtualizado = timeService.update(time);

            return ResponseEntity.ok(timeAtualizado);
        }catch (ObjectNotFoundException ex){
            return criarTime(time);
        }
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable Long id){
        timeService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<Time> criarTime(Time time){
        Time timeCriado = timeService.create(time);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("/{id}").buildAndExpand(timeCriado.getId()).toUri();

                return ResponseEntity.created(uri).build();
    }

    private Time inToEntity(TimeInDTO dto){
        Time time = new Time();
        time.setId(time.getId());
        time.setNome(dto.getNome());

        return time;
    }

    private TimeOutDTO entityToOut(Time time){
        TimeOutDTO dto = new TimeOutDTO();

        dto.setId(time.getId());
        dto.setNome(time.getNome());
        dto.setPontuacao(time.getPontuacao());
        dto.setContratos(time.getContratos());

        return dto;
    }

}
