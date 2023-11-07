package com.feverdunk.site.service;

import com.feverdunk.site.exceptions.AuthorizationException;
import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Manager;
import com.feverdunk.site.models.Perfil;
import com.feverdunk.site.models.Time;
import com.feverdunk.site.repository.ManagerRepository;
import com.feverdunk.site.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContext;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Service
public class ManagerService {
    private final ManagerRepository managerRepository;
    private BCryptPasswordEncoder bCryptPasswordEncoder;

    @Autowired
    public ManagerService(ManagerRepository managerRepository, BCryptPasswordEncoder bCryptPasswordEncoder) {
        this.managerRepository = managerRepository;
        this.bCryptPasswordEncoder = bCryptPasswordEncoder;
    }

    public List<Manager> getManager() {
        UserSpringSecurity userSpringSecurity = ManagerService.authenticated();
        if (Objects.nonNull(userSpringSecurity) && userSpringSecurity.hasHole(Perfil.ADMIN)) {
            return this.managerRepository.findAll();
        }

        throw new AuthorizationException("Acesso negado");
    }

    public Manager findById(Long id) {
        UserSpringSecurity userSpringSecurity = authenticated();
        if (Objects.nonNull(userSpringSecurity) && (userSpringSecurity.hasHole(Perfil.ADMIN) || id.equals(userSpringSecurity.getId()))) {
            Optional<Manager> manager = this.managerRepository.findById(id);
            return manager.orElseThrow(() -> {
                return new ObjectNotFoundException("Manager com id: {" + id + "} não foi encontrado");
            });
        } else {
            throw new AuthorizationException("Acesso negado.");
        }
    }

    @Transactional
    public Manager create(Manager manager) {
        manager.setId(null);
        manager.setSenha(this.bCryptPasswordEncoder.encode(manager.getSenha()));
        manager.setTime(new Time());
        manager.setPerfis(Stream.of(Perfil.USER.getCodigo()).collect(Collectors.toSet()));
        return this.managerRepository.save(manager);
    }

    @Transactional
    public Manager update(Manager managerNovo) {
        Manager manager = this.findById(managerNovo.getId());
        manager.setDinheiro(managerNovo.getDinheiro());
        manager.setEmail(managerNovo.getEmail());
        manager.setNome(managerNovo.getNome());
        manager.setSenha(managerNovo.getSenha());
        manager.setSenha(this.bCryptPasswordEncoder.encode(managerNovo.getSenha()));
        manager.setPremium(managerNovo.isPremium());
        manager.setTime(managerNovo.getTime());
        manager.setPerfis(Stream.of(Perfil.USER.getCodigo()).collect(Collectors.toSet()));
        return this.managerRepository.save(manager);
    }

    public static UserSpringSecurity authenticated() {
        try {
            SecurityContext context = SecurityContextHolder.getContext();
            Authentication authentication = context.getAuthentication();
            Object principal = authentication.getPrincipal();
            return (UserSpringSecurity)SecurityContextHolder.getContext().getAuthentication().getPrincipal();
        } catch (Exception var3) {
            return null;
        }
    }

    public void delete(Long id) {
        this.managerRepository.delete(this.findById(id));
    }
}
