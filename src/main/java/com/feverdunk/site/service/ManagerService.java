package com.feverdunk.site.service;

import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Manager;
import com.feverdunk.site.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;

    @Autowired
    public ManagerService(ManagerRepository managerRepository){ this.managerRepository = managerRepository; }

    public List<Manager> getManager(){
        return managerRepository.findAll();
    }

    public Manager findById(Long id) {
        Optional<Manager> manager = managerRepository.findById(id);

        return manager.orElseThrow(() -> new ObjectNotFoundException("Manager com id: {" + id + "} não foi encontrado"));
    }

    public Manager create(Manager manager) {
        manager.setId(null);

        return managerRepository.save(manager);
    }

    public Manager update(Manager managerNovo) {
        Manager manager = findById(managerNovo.getId());

        manager.setDinheiro(managerNovo.getDinheiro());
        manager.setEmail(managerNovo.getEmail());
        manager.setNome(managerNovo.getNome());
        manager.setSenha(managerNovo.getSenha());
        manager.setPremium(managerNovo.isPremium());
        manager.setTime(managerNovo.getTime());

        return managerRepository.save(manager);
    }

    public void delete(Long id){
        managerRepository.delete(findById(id));
    }
}
