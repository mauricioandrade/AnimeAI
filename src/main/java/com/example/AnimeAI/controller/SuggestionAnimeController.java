package com.example.AnimeAI.controller;

import com.example.AnimeAI.model.AnimeModel;
import com.example.AnimeAI.service.AnimeService;
import com.example.AnimeAI.service.OpenAIService;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
public class SuggestionAnimeController {

    private AnimeService service;
    private final OpenAIService openAIService;

    public SuggestionAnimeController(OpenAIService openAIService, AnimeService service) {
        this.openAIService = openAIService;
        this.service = service;
    }

    @GetMapping(value = "/suggestion", produces = MediaType.TEXT_PLAIN_VALUE)
    public Mono<ResponseEntity<String>> suggestionAnime() {
        List<AnimeModel> animeModels = service.findAll();
        return openAIService.suggestionAnime(animeModels)
                .map(body -> ResponseEntity.ok().body(body))
                .onErrorResume(e -> Mono.just(
                        ResponseEntity.status(HttpStatus.BAD_GATEWAY)
                                .body("Erro ao obter sugestão: " + e.getMessage())
                ));
    }
}