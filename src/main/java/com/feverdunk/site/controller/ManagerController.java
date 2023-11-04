package com.feverdunk.site.controller;

import com.feverdunk.site.models.Manager;
import com.feverdunk.site.service.ManagerService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping
public class ManagerController {

    private final ManagerService managerService;

    @Autowired
    public ManagerController(ManagerService managerService){ this.managerService = managerService; }

    @GetMapping
    public List<Manager> getManager(){
        return managerService.getManager();
    }
}
