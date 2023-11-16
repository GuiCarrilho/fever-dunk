package com.feverdunk.site.service;

import com.feverdunk.site.exceptions.AuthorizationException;
import com.feverdunk.site.exceptions.EntityAlreadyExistsExeption;
import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Manager;
import com.feverdunk.site.models.Perfil;
import com.feverdunk.site.models.Time;
import com.feverdunk.site.repository.TimeRepository;
import com.feverdunk.site.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class TimeService {

    private final TimeRepository timeRepository;
    private final ManagerService managerService;

    @Autowired
    public TimeService(TimeRepository timeRepository, ManagerService managerService) {
        this.timeRepository = timeRepository;
        this.managerService = managerService;
    }

    public List<Time> getTime() {return timeRepository.findAll();}

    /*public Time findByManager(){
        UserSpringSecurity userSpringSecurity = ManagerService.authenticated();
        if(Objects.nonNull(userSpringSecurity)){
            var a = userSpringSecurity.getId();
            Optional<Time> time = timeRepository.findTimeByManagerId(userSpringSecurity.getId());

            return time.orElseThrow(() -> new ObjectNotFoundException("Manager com id: {" +
                    userSpringSecurity.getId() + "} não possui time."));
        }

        throw new AuthorizationException("Acesso negado.");
    }*/

    public Time findById(String id){
        Optional<Time> time = timeRepository.findById(id);

        return time.orElseThrow(() -> new ObjectNotFoundException("Time com id: {" + id + "} não foi encontrado"));
    }

    @Transactional
    public Time create(Time time){
        UserSpringSecurity userSpringSecurity = ManagerService.authenticated();
        if(Objects.nonNull(userSpringSecurity)){
            Manager manager = managerService.findById(userSpringSecurity.getId());
            if(Objects.isNull(manager.getTime())){
                time.setId(null);
                Time timeCriado = timeRepository.save(time);
                manager.setTime(timeCriado);

                return timeCriado;
            }
            else{
                throw new EntityAlreadyExistsExeption("O manager id: {" + manager.getId() + "} já possio time");
            }
        }
        else{
            throw new AuthorizationException("Acesso negado.");
        }
    }

    @Transactional
    public Time update(Time timeNovo){
        Time time = findById(timeNovo.getId());

        time.setNome(timeNovo.getNome());
        time.setPontuacao(timeNovo.getPontuacao());
        time.setContratos(timeNovo.getContratos());

        return timeRepository.save(time);
    }

    public void delete(String id){
        UserSpringSecurity userSpringSecurity = ManagerService.authenticated();
        if(Objects.nonNull(userSpringSecurity) && userSpringSecurity.hasHole(Perfil.ADMIN)) {
            timeRepository.delete(findById(id));
        }

        throw new AuthorizationException("Acesso negado.");
    }

    public List<Time> getTimeFromLiga(String LigaId) { return timeRepository.findAllByParticipacoes_Liga_id(LigaId); }
}
