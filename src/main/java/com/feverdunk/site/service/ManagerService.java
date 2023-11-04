package com.feverdunk.site.service;

import com.feverdunk.site.models.Manager;
import com.feverdunk.site.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository){ this.managerRepository = managerRepository; }

    public List<Manager> getManager(){
        return managerRepository.findAll();
    }
}
