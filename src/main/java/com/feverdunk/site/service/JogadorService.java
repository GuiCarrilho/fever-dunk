package com.feverdunk.site.service;

import com.feverdunk.site.exceptions.AuthorizationException;
import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Jogador;
import com.feverdunk.site.models.Perfil;
import com.feverdunk.site.repository.JogadorRepository;
import com.feverdunk.site.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class JogadorService {
    private final JogadorRepository jogadorRepository;
    private final ManagerService managerService;

    @Autowired
    public JogadorService(JogadorRepository jogadorRepository, ManagerService managerService){
        this.jogadorRepository = jogadorRepository;
        this.managerService = managerService;
    }

    public List<Jogador> getJogador(){

        return jogadorRepository.findAll();
    }

    public Jogador findById(String id){
        Optional<Jogador> jogador = jogadorRepository.findById(id);

        return jogador.orElseThrow(() -> new ObjectNotFoundException("Jogador com id: {" + id + "} não foi encontrado"));
    }

    public List<Jogador> findAllByTimeId(String timeId){
        return jogadorRepository.findAllByContratos_Time_id(timeId);
    }

    @Transactional
    public Jogador create(Jogador jogador){
        if(ehAdmin()) {
            jogador.setId(null);

            return jogadorRepository.save(jogador);
        }
        else{
            throw new AuthorizationException("Acesso negado.");
        }
    }

    @Transactional
    public Jogador update(Jogador jogadorNovo){
        if(ehAdmin()){
            return jogadorRepository.save(jogadorNovo);
        }
        else{
            throw new AuthorizationException("Acesso negado.");
        }
    }

    public void delete(String id){
        if(ehAdmin()) {
            jogadorRepository.delete(findById(id));
        }
        else {
            throw new AuthorizationException("Acesso negado.");
        }
    }

    private boolean ehAdmin(){
        UserSpringSecurity userSpringSecurity = ManagerService.authenticated();
        return Objects.nonNull(userSpringSecurity) && userSpringSecurity.hasHole(Perfil.ADMIN);
    }

}
