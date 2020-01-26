package com.war.alliance.service;

import com.war.alliance.document.Crusader;
import com.war.alliance.repository.CrusaderRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@Service
public class CrusaderServiceImpl implements CrusaderService {

    @Autowired
    private CrusaderRepository repository;

    @Override
    public Flux<Crusader> getAllCrusaders() {
        return repository.findAll();
    }

    @Override
    public Mono<Crusader> addCrusader(Crusader crusader) {
        return repository.save(crusader);
    }
}