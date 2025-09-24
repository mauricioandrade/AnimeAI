package com.example.AnimeAI.controller;

import com.example.AnimeAI.model.AnimeModel;
import com.example.AnimeAI.service.AnimeService;
import com.example.AnimeAI.service.OpenAIService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.List;

@RestController
@Tag(name = "Sugestões", description = "Endpoints que utilizam a OpenAI para gerar recomendações")
public class SuggestionAnimeController {

    private final AnimeService service;
    private final OpenAIService openAIService;

    public SuggestionAnimeController(OpenAIService openAIService, AnimeService service) {
        this.openAIService = openAIService;
        this.service = service;
    }

    @GetMapping(value = "/suggestion", produces = MediaType.TEXT_PLAIN_VALUE)
    @Operation(summary = "Sugestão de animes", description = "Gera recomendações em texto simples a partir do catálogo atual")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Sugestões retornadas com sucesso",
                    content = @Content(mediaType = MediaType.TEXT_PLAIN_VALUE,
                            schema = @Schema(implementation = String.class))),
            @ApiResponse(responseCode = "502", description = "Erro ao consultar a OpenAI", content = @Content)
    })
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
