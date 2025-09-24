package com.example.AnimeAI.controller;

import com.example.AnimeAI.model.AnimeModel;
import com.example.AnimeAI.model.Categoria;
import com.example.AnimeAI.service.AnimeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import io.swagger.v3.oas.annotations.tags.Tag;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/anime")
@Tag(name = "Animes", description = "Operações de gerenciamento do catálogo de animes")
public class AnimeController {

    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping
    @Operation(summary = "Listar todos os animes", description = "Retorna a lista completa de animes cadastrados")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Animes retornados com sucesso",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnimeModel.class)))
    })
    public ResponseEntity<List<AnimeModel>> findAll(){
        List<AnimeModel> animes = animeService.findAll();
        return ResponseEntity.ok(animes);
    }

    @GetMapping("/{id}")
    @Operation(summary = "Buscar anime por ID", description = "Recupera um anime específico usando o identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Anime encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnimeModel.class))),
            @ApiResponse(responseCode = "404", description = "Anime não encontrado", content = @Content)
    })
    public ResponseEntity<AnimeModel> findById(@Parameter(description = "Identificador único do anime", example = "1")
                                               @PathVariable Long id){
        Optional<AnimeModel> anime = animeService.findById(id);
        return anime.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/titulo/{titulo}")
    @Operation(summary = "Buscar anime por título", description = "Retorna o anime que possui o título informado")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Anime encontrado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnimeModel.class))),
            @ApiResponse(responseCode = "404", description = "Anime não encontrado", content = @Content)
    })
    public ResponseEntity<AnimeModel> findByTitulo(@Parameter(description = "Título completo do anime", example = "Naruto")
                                                   @PathVariable String titulo) {
        Optional<AnimeModel> anime = animeService.findByTitulo(titulo);
        return anime.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    @Operation(summary = "Listar por categoria", description = "Filtra os animes pela categoria informada")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Lista de animes da categoria",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnimeModel.class)))
    })
    public ResponseEntity<List<AnimeModel>> findByCategorias(@Parameter(description = "Categoria do anime", example = "SHOUNEN")
                                                             @PathVariable Categoria categoria) {
        List<AnimeModel> anime = animeService.findByCategoria(categoria);
        return ResponseEntity.ok(anime);
    }

    @PostMapping
    @Operation(summary = "Criar um novo anime", description = "Persiste um novo anime no catálogo")
    @ApiResponses({
            @ApiResponse(responseCode = "201", description = "Anime criado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnimeModel.class))),
            @ApiResponse(responseCode = "400", description = "Requisição inválida", content = @Content)
    })
    public ResponseEntity<AnimeModel> save(@Validated @RequestBody AnimeModel anime) {
        try {
            AnimeModel animeSalvo = animeService.save(anime);
            return ResponseEntity.status(HttpStatus.CREATED).body(animeSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    @Operation(summary = "Atualizar anime", description = "Atualiza os dados de um anime existente")
    @ApiResponses({
            @ApiResponse(responseCode = "200", description = "Anime atualizado",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = AnimeModel.class))),
            @ApiResponse(responseCode = "404", description = "Anime não encontrado", content = @Content)
    })
    public ResponseEntity<AnimeModel> updateById(@Parameter(description = "Identificador do anime", example = "1")
                                                 @PathVariable Long id,
                                                 @Validated @RequestBody AnimeModel anime) {
        Optional<AnimeModel> animeAtualizado = animeService.updateById(id, anime);
        return animeAtualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "Remover anime", description = "Exclui um anime do catálogo a partir do identificador")
    @ApiResponses({
            @ApiResponse(responseCode = "204", description = "Anime removido", content = @Content),
            @ApiResponse(responseCode = "404", description = "Anime não encontrado", content = @Content)
    })
    public ResponseEntity<Void> deleteById(@Parameter(description = "Identificador do anime", example = "1")
                                           @PathVariable Long id) {
        if (animeService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
