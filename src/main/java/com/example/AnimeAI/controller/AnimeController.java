package com.example.AnimeAI.controller;

import com.example.AnimeAI.model.AnimeModel;
import com.example.AnimeAI.model.Categoria;
import com.example.AnimeAI.service.AnimeService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/anime")
public class AnimeController {

    private final AnimeService animeService;

    public AnimeController(AnimeService animeService) {
        this.animeService = animeService;
    }

    @GetMapping
    public ResponseEntity<List<AnimeModel>> findaAll(){
        List<AnimeModel> animes = animeService.findAll();
        return ResponseEntity.ok(animes);
    }

    @GetMapping("/{id}")
    public ResponseEntity<AnimeModel> findById(@PathVariable Long id){
        Optional<AnimeModel> anime = animeService.findById(id);
        return anime.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/titulo/{titulo}")
    public ResponseEntity<AnimeModel> findByTitulo(@PathVariable String titulo) {
        Optional<AnimeModel> anime = animeService.findByTitulo(titulo);
        return anime.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @GetMapping("/categoria/{categoria}")
    public ResponseEntity<List<AnimeModel>> findByCategorias(@PathVariable Categoria categoria) {
        List<AnimeModel> anime = animeService.findByCategoria(categoria);
        return ResponseEntity.ok(anime);
    }

    @PostMapping
    public ResponseEntity<AnimeModel> save(@Validated @RequestBody AnimeModel anime) {
        try {
            AnimeModel animeSalvo = animeService.save(anime);
            return ResponseEntity.status(HttpStatus.CREATED).body(animeSalvo);
        } catch (Exception e) {
            return ResponseEntity.badRequest().build();
        }
    }

    @PutMapping("/{id}")
    public ResponseEntity<AnimeModel> updateById(@PathVariable Long id, @Validated @RequestBody AnimeModel anime) {
        Optional<AnimeModel> animeAtualizado = animeService.updateById(id, anime);
        return animeAtualizado.map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteById(@PathVariable Long id) {
        if (animeService.deleteById(id)) {
            return ResponseEntity.noContent().build();
        }
        return ResponseEntity.notFound().build();
    }
}
