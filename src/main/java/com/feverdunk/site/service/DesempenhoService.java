package com.feverdunk.site.service;

import com.feverdunk.site.exceptions.AuthorizationException;
import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Desempenho;
import com.feverdunk.site.models.Perfil;
import com.feverdunk.site.repository.DesempenhoRepository;
import com.feverdunk.site.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class DesempenhoService {

    private final DesempenhoRepository desempenhoRepository;
    private ManagerService managerService;

    @Autowired
    public DesempenhoService(DesempenhoRepository desempenhoRepository, ManagerService managerService) {
        this.desempenhoRepository = desempenhoRepository;
        this.managerService = managerService;
    }

    public List<Desempenho> getDesempenho() { return desempenhoRepository.findAll(); }

    public Desempenho findById(String id){
        Optional<Desempenho> desempenho = desempenhoRepository.findById(id);

        return desempenho.orElseThrow(() -> new ObjectNotFoundException("Desempenho com id: {" + id + "} não foi encontrado"));
    }

    @Transactional
    public Desempenho create(Desempenho desempenho){
        if(ehAdmin()) {
            desempenho.setId(null);

            return desempenhoRepository.save(desempenho);
        }

        throw new AuthorizationException("Acesso negado.");
    }

    @Transactional
    public Desempenho update(Desempenho desempenhoNovo){
        if(ehAdmin()) {
            Desempenho desempenho = findById(desempenhoNovo.getId());

            desempenho.setData(desempenhoNovo.getData());
            desempenho.setPontos(desempenhoNovo.getPontos());
            desempenho.setRebotes(desempenhoNovo.getRebotes());
            desempenho.setAssistencias(desempenhoNovo.getAssistencias());
            desempenho.setMinJogados(desempenhoNovo.getMinJogados());
            desempenho.setJogador(desempenhoNovo.getJogador());

            return desempenhoRepository.save(desempenho);
        }

        throw new AuthorizationException("Acesso negado.");
    }

    public void delete(String id) {
        if(ehAdmin()){
            desempenhoRepository.delete(findById(id));
        }
        else{
            throw new AuthorizationException("Acesso negado.");
        }
    }

    private boolean ehAdmin(){
        UserSpringSecurity userSpringSecurity = ManagerService.authenticated();
        return Objects.nonNull(userSpringSecurity) && userSpringSecurity.hasHole(Perfil.ADMIN);
    }

}
