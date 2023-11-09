package com.feverdunk.site.service;

import com.feverdunk.site.exceptions.*;
import com.feverdunk.site.models.*;
import com.feverdunk.site.models.compositeIDs.ContratoId;
import com.feverdunk.site.repository.ContratoRepository;
import com.feverdunk.site.security.UserSpringSecurity;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Objects;
import java.util.Optional;

@Service
public class ContratoService {

    private final ContratoRepository contratoRepository;
    private ManagerService managerService;
    private JogadorService jogadorService;
    private TimeService timeService;

    @Autowired
    public ContratoService(ContratoRepository contratoRepository, ManagerService managerService,
                           JogadorService jogadorService, TimeService timeService){
        this.contratoRepository = contratoRepository;
        this.managerService = managerService;
        this.jogadorService = jogadorService;
        this.timeService = timeService;
    }

    public List<Contrato> getContrato() {
        UserSpringSecurity userSpringSecurity = ManagerService.authenticated();
        if(Objects.nonNull(userSpringSecurity) && userSpringSecurity.hasHole(Perfil.ADMIN)) {
            return contratoRepository.findAll();
        }
        throw new AuthorizationException("Acesso negado.");
    }

    public Contrato findById(ContratoId id) {
        if(temAutorizacao(id.getTimeId())){
            Optional<Contrato> contrato = contratoRepository.findById(id);

            return contrato.orElseThrow(() -> new ObjectNotFoundException("Contrato com id: {" + id + "} não foi encontrado"));
        }

        throw new AuthorizationException("Acesso negado.");
    }

    public List<Contrato> findAllByTimeId(Long timeId){
        if(temAutorizacao(timeId)) {
            return contratoRepository.findAllByTimeId(timeId);
        }

        throw new AuthorizationException("Acesso negado.");
    }

    public List<Contrato> findAllByJogadorId(Long jogadorId){
        if(temAutorizacao(jogadorId)) {
            return contratoRepository.findAllByJogadorId(jogadorId);
        }

        throw new AuthorizationException("Acesso negado.");
    }

    @Transactional
    public Contrato create(Contrato contrato) {
        Long timeId = contrato.getId().getTimeId();;
        if(temAutorizacao(timeId)){
            List<Contrato> contratos = findAllByTimeId(timeId);
            if(contratos.size() >= 5){
                throw new MaxSizeException("O time pode ter no máximo 5 jogadores.");
            }
            Jogador jogador = jogadorService.findById(contrato.getId().getJogadorId());
            Time time = timeService.findById(contrato.getId().getTimeId());
            Manager manager = managerService.findById(ManagerService.authenticated().getId());
            for(Contrato c: contratos){
                if(c.getJogador().getId().equals(jogador.getId()) && Objects.isNull(c.getVendidoEm())){
                    throw new EntityAlreadyExistsExeption("O jogador com id: {" + jogador.getId() +"} já faz parte do time.");
                }
            }
            if(manager.getDinheiro() < jogador.getValor()){
                throw new NotEnoughFundsException("O manager com id: {" + manager.getId() + "} não tem +" +
                        "fundos suficientes para adquirir o jogador com id {" + jogador.getId()+ "}.");
            }
            contrato.setTime(time);
            contrato.setJogador(jogador);
            contrato.setAdquiridoEm(LocalDateTime.now());
            int dinheiroResultante = manager.getDinheiro() - jogador.getValor();
            manager.setDinheiro(dinheiroResultante);
            managerService.update(manager);
            return contratoRepository.save(contrato);
        }

        throw new AuthorizationException("Acesso negado.");
    }

    @Transactional
    public Contrato update(Contrato contratoNovo) { //Só ADMIN pode atualizar um contrato
        UserSpringSecurity userSpringSecurity = ManagerService.authenticated();
        if(Objects.nonNull(userSpringSecurity) && userSpringSecurity.hasHole(Perfil.ADMIN)){
            Contrato contrato = findById(contratoNovo.getId());

            contrato.setTime(contratoNovo.getTime());
            contrato.setJogador(contratoNovo.getJogador());
            contrato.setAdquiridoEm(contratoNovo.getAdquiridoEm());
            contrato.setVendidoEm(contratoNovo.getVendidoEm());

            return contratoRepository.save(contrato);
        }

        throw new AuthorizationException("Acesso negado.");
    }

    public void delete(ContratoId id) {
        Contrato contrato = findById(id);
        LocalDateTime data = LocalDateTime.now();
        contrato.setVendidoEm(data);
        contratoRepository.save(contrato);
    }

    private boolean temAutorizacao(Long timeId){
        UserSpringSecurity userSpringSecurity = ManagerService.authenticated();
        return Objects.nonNull(userSpringSecurity) && (userSpringSecurity.hasHole(Perfil.ADMIN) ||
                timeId.equals(managerService.findById(userSpringSecurity.getId()).getTime().getId()));
    }
}