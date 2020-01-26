package com.war.alliance.controller;

import com.war.alliance.document.Crusader;
import com.war.alliance.service.CrusaderService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

@RestController
@RequestMapping("/alliance/crusader")
public class CrusaderController {

    @Autowired
    private CrusaderService service;

    @GetMapping
    public Flux<Crusader> getCrusaderList() {
        return service.getAllCrusaders();
    }

    @PostMapping
    public Mono<Crusader> addCrusader(@RequestBody Crusader crusader) {
        return service.addCrusader(crusader);
    }
}