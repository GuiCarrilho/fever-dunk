package com.feverdunk.site.controller;

import com.feverdunk.site.dto.contrato.ContratoInDTO;
import com.feverdunk.site.dto.contrato.ContratoOutDTO;
import com.feverdunk.site.models.Jogador;
import com.feverdunk.site.models.Time;
import com.feverdunk.site.models.compositeIDs.ContratoId;
import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Contrato;
import com.feverdunk.site.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.support.ServletUriComponentsBuilder;

import java.net.URI;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/contrato")
public class ContratoController {

    private final ContratoService contratoService;

    @Autowired
    public ContratoController(ContratoService contratoService) {
        this.contratoService = contratoService;
    }

    @GetMapping("/all")
    public ResponseEntity<List<ContratoOutDTO>> getContrato() {
        List<Contrato> contratos = contratoService.getContrato();
        List<ContratoOutDTO> contratosDto = contratos.stream()
                .map(this::entityToOut).collect(Collectors.toList());
        return ResponseEntity.ok(contratosDto);
    }

    @GetMapping("/time/{id}")
    public ResponseEntity<List<ContratoOutDTO>> getContratoByTimeId(@PathVariable String id) {
        List<Contrato> contratos = contratoService.findAllByTimeId(id);
        List<ContratoOutDTO> contratosDto = contratos.stream()
                .map(this::entityToOut).collect(Collectors.toList());
        return ResponseEntity.ok(contratosDto);
    }

    @GetMapping("/jogador/{id}")
    public ResponseEntity<List<ContratoOutDTO>> getContratoByJogadorId(@PathVariable String id) {
        List<Contrato> contratos = contratoService.findAllByJogadorId(id);
        List<ContratoOutDTO> contratosDto = contratos.stream()
                .map(this::entityToOut).collect(Collectors.toList());
        return ResponseEntity.ok(contratosDto);
    }

    @PostMapping
    public ResponseEntity<ContratoOutDTO> post(@RequestBody @Validated ContratoInDTO dto) {
        Contrato contrato = inToEntity(dto);
        return criarContrato(contrato);
    }

    @PutMapping
    public ResponseEntity<ContratoOutDTO> put(@RequestBody @Validated ContratoInDTO dto) {
        String contratoId = inToEntity(dto).getId();
        Contrato contrato = inToEntity(dto);
        if(contrato.getTime().getId() != null && contrato.getJogador().getId() != null) {
            try {
                getContratoByTimeId(dto.getTimeId());
                getContratoByJogadorId(dto.getJogadorId());
                ContratoOutDTO outDTO = entityToOut(contratoService.update(contrato));
                return ResponseEntity.ok(outDTO);
            } catch (ObjectNotFoundException ex) {

                return criarContrato(contrato);
            }
        }
        else return criarContrato(contrato);
    }


    @DeleteMapping("/{id}")
    public ResponseEntity<Void> delete(@PathVariable String id) {
        contratoService.delete(id);

        return ResponseEntity.noContent().build();
    }

    private ResponseEntity<ContratoOutDTO> criarContrato(Contrato contrato) {
        Contrato contratoCriada = contratoService.create(contrato);

        URI uri = ServletUriComponentsBuilder.fromCurrentContextPath()
                .path("{/id}").buildAndExpand(contratoCriada.getId()).toUri();

        return ResponseEntity.created(uri).build();
    }

    private Contrato inToEntity(ContratoInDTO dto) {
        ContratoId contratoId = new ContratoId();
        Contrato contrato = new Contrato();
        if(dto.getJogadorId() == null || dto.getTimeId() == null){
            throw new IllegalArgumentException();
        }
        Time time = new Time();
        time.setId(dto.getTimeId());
        Jogador jogador = new Jogador();
        jogador.setId(dto.getJogadorId());

        contrato.setTime(time);
        contrato.setJogador(jogador);


        return contrato;
    }

    private ContratoOutDTO entityToOut(Contrato contrato){
        ContratoId contratoId = new ContratoId();
        if(contratoId.getJogadorId() == null || contratoId.getTimeId() == null){
            throw new IllegalArgumentException();
        }
        ContratoOutDTO dto = new ContratoOutDTO();
        dto.setJogadorId(contratoId.getJogadorId());
        dto.setTimeId(contratoId.getTimeId());
        dto.setAdquiridoEm(contrato.getAdquiridoEm());
        dto.setVendidoEm(contrato.getVendidoEm());

        return dto;
    }
}
