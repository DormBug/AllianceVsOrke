package com.war.alliance.service;

import com.war.alliance.document.Crusader;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

public interface CrusaderService {

    Flux<Crusader> getAllCrusaders();

    Mono<Crusader> addCrusader(Crusader crusader);
}