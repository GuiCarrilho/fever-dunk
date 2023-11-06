package com.feverdunk.site.service;

import com.feverdunk.site.exceptions.GlobalExceptionHandler;
import com.feverdunk.site.exceptions.ObjectNotFoundException;
import com.feverdunk.site.models.Liga;
import com.feverdunk.site.models.Time;
import com.feverdunk.site.repository.TimeRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class TimeService {

    private TimeRepository timeRepository;

    @Autowired
    public TimeService(TimeRepository timeRepository) {this.timeRepository = timeRepository;}

    public List<Time> getTime() {return timeRepository.findAll();}

    public Time findById(Long id){
        Optional<Time> time = timeRepository.findById(id);

        return time.orElseThrow(() -> new ObjectNotFoundException("Time com id: {" + id + "} não foi encontrado"));
    }

    public Time create(Time time){
        time.setId(null);

        return timeRepository.save(time);
    }

    public Time update(Time timeNovo){
        Time time = findById(timeNovo.getId());

        time.setNome(timeNovo.getNome());
        time.setPontuacao(timeNovo.getPontuacao());
        time.setContratos(timeNovo.getContratos());

        return timeRepository.save(time);
    }
    public void delete(Long id){
        timeRepository.delete(findById(id));
    }

    public List<Time> getTimeFromLiga(Long LigaId) { return timeRepository.findTimesFromLiga(LigaId); }
}
