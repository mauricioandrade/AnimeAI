package com.example.AnimeAI.service;

import com.example.AnimeAI.model.AnimeModel;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import reactor.core.publisher.Mono;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@Service
public class OpenAIService {

    private final WebClient webClient;
    public OpenAIService(WebClient webClient) {
        this.webClient = webClient;
    }

    public Mono<String> suggestionAnime(List<AnimeModel> animeModels) {

        String titulos = animeModels.stream()
                .map(a -> String.format("%s (Categoria: %s)", a.getTitulo(), a.getCategoria()))
                .collect(Collectors.joining("\n"));

        String prompt = "Baseado no meu banco de dados, sugira alguns animes parecidos: \n" +titulos;
        Map<String, Object> body = Map.of(
                "model", "gpt-4o-mini",
                "messages", List.of(
                        Map.of("role", "system", "content", "Você vai recomendar animes"),
                        Map.of("role", "user", "content", prompt)
                )
        );

        return webClient.post()
                .uri("/chat/completions")
                .bodyValue(body)
                .retrieve()
                .onStatus(HttpStatusCode::isError, resp ->
                        resp.bodyToMono(String.class)
                                .flatMap(err -> Mono.error(new RuntimeException(
                                        "OpenAI error: " + resp.statusCode() + " - " + err)))
                )
                .bodyToMono(Map.class)
                .map(res -> {
                    var choices = (List<Map<String, Object>>) res.get("choices");
                    if (choices == null || choices.isEmpty()) return "Nenhum anime foi sugerido";
                    var message = (Map<String, Object>) choices.get(0).get("message");
                    return String.valueOf(message.get("content"));
                })
                .onErrorResume(e -> Mono.just("Erro ao obter sugestão: " + e.getMessage()));
    }
}