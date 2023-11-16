package com.feverdunk.site.service;

import com.feverdunk.site.exceptions.AuthorizationException;
import com.feverdunk.site.exceptions.EntityAlreadyExistsExeption;
import com.feverdunk.site.models.Perfil;
import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Participacao;
import com.feverdunk.site.repository.ParticipacaoRepository;
import com.feverdunk.site.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ParticipacaoService {

    private final ParticipacaoRepository participacaoRepository;
    private final ManagerService managerService;

    @Autowired
    public ParticipacaoService(ParticipacaoRepository participacaoRepository, ManagerService managerService){
        this.participacaoRepository = participacaoRepository;
        this.managerService = managerService;
    }

    public List<Participacao> getParticipao(){
        UserSpringSecurity userSpringSecurity = ManagerService.authenticated();
        if(Objects.nonNull(userSpringSecurity) && userSpringSecurity.hasHole(Perfil.ADMIN)) {
            return participacaoRepository.findAll();
        }

        throw new AuthorizationException("Acesso negado.");
    }

    public Participacao findById(String id) {
        if (temAutorizacao(id)) {
            Optional<Participacao> participacao = participacaoRepository.findById(id);
            return participacao.orElseThrow(() -> new ObjectNotFoundException("Participação com id: {" + id + "} não foi encontrado"));
        }

        return null;
    }

    @Transactional
    public Participacao create(Participacao participacao, String senha){
        try{
            findById(participacao.getId());
            throw new EntityAlreadyExistsExeption("Jogador já está presente na liga.");
        }
        catch (ObjectNotFoundException ex){
            if(Objects.nonNull(participacao.getLiga().getSenha()) && senha.equals(participacao.getLiga().getSenha())){
                return participacaoRepository.save(participacao);
            }

            throw new AuthorizationException("Senha da liga está incorreta.");
        }
    }

    @Transactional
    public Participacao update(Participacao participacaoNova) {
        UserSpringSecurity userSpringSecurity = ManagerService.authenticated();
        if(Objects.nonNull(userSpringSecurity) && userSpringSecurity.hasHole(Perfil.ADMIN)) {
            Participacao participacao = findById(participacaoNova.getId());

            participacao.setData(participacaoNova.getData());
            participacao.setAte(participacaoNova.getAte());
            participacao.setTime(participacaoNova.getTime());
            participacao.setLiga(participacaoNova.getLiga());

            return participacaoRepository.save(participacaoNova);
        }

        throw new AuthorizationException("Acesso negado.");
    }

    public void delete(String id){
        Participacao participacao = findById(id);
        LocalDateTime data = LocalDateTime.now();
        participacao.setAte(data);
        participacaoRepository.save(participacao);
    }

    private boolean temAutorizacao(String timeId){
        UserSpringSecurity userSpringSecurity = ManagerService.authenticated();
        return Objects.nonNull(userSpringSecurity) && (userSpringSecurity.hasHole(Perfil.ADMIN) ||
                timeId.equals(managerService.findById(userSpringSecurity.getId()).getTime().getId()));
    }
}
