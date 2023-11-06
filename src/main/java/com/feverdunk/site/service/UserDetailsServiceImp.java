package com.feverdunk.site.service;

import com.feverdunk.site.models.Manager;
import com.feverdunk.site.repository.ManagerRepository;
import com.feverdunk.site.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
public class UserDetailsServiceImp implements UserDetailsService {
    private ManagerRepository managerRepository;

    @Autowired
    public UserDetailsServiceImp(ManagerRepository managerRepository) {
        this.managerRepository = managerRepository;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Manager manager = managerRepository.findByEmail(username).orElseThrow(() ->
                new UsernameNotFoundException("Email não encontrado."));
        return new UserSpringSecurity(manager.getId(),
                manager.getEmail(),
                manager.getSenha(),
                manager.getPerfis());
    }
}
