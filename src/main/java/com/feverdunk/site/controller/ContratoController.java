package com.feverdunk.site.controller;

import com.feverdunk.site.models.Contrato;
import com.feverdunk.site.service.ContratoService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/contrato")
public class ContratoController {

    private final ContratoService contratoService;

    @Autowired
    public ContratoController(ContratoService contratoService){this.contratoService = contratoService;}

    @GetMapping
    public List<Contrato> getContrato(){
        return contratoService.getContrato();
    }
}
