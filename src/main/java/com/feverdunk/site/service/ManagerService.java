package com.feverdunk.site.service;

import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Manager;
import com.feverdunk.site.models.Perfil;
import com.feverdunk.site.repository.ManagerRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ManagerService {

    private final ManagerRepository managerRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ManagerService(ManagerRepository managerRepository, BCryptPasswordEncoder bCryptPasswordEncoder){
        this.managerRepository = managerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<Manager> getManager(){
        return managerRepository.findAll();
    }

    public Manager findById(Long id) {
        Optional<Manager> manager = managerRepository.findById(id);

        return manager.orElseThrow(() -> new ObjectNotFoundException("Manager com id: {" + id + "} não foi encontrado"));
    }

    public Manager create(Manager manager) {
        manager.setId(null);
        manager.setSenha(bCryptPasswordEncoder.encode(manager.getSenha()));
        return managerRepository.save(manager);
    }

    public Manager update(Manager managerNovo) {
        Manager manager = findById(managerNovo.getId());

        manager.setDinheiro(managerNovo.getDinheiro());
        manager.setEmail(managerNovo.getEmail());
        manager.setNome(managerNovo.getNome());
        manager.setSenha(managerNovo.getSenha());
        manager.setSenha(bCryptPasswordEncoder.encode(managerNovo.getSenha()));
        manager.setPremium(managerNovo.isPremium());
        manager.setTime(managerNovo.getTime());
        manager.setPerfis(Stream.of(Perfil.USER.getCodigo()).collect(Collectors.toSet()));

        return managerRepository.save(manager);
    }

    public void delete(Long id){
        managerRepository.delete(findById(id));
    }
}
