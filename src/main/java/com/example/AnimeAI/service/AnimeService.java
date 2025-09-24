package com.example.AnimeAI.service;

import com.example.AnimeAI.model.AnimeModel;
import com.example.AnimeAI.model.Categoria;
import com.example.AnimeAI.repository.AnimeRepository;
import org.springframework.stereotype.Service;

import java.text.Normalizer;
import java.util.List;
import java.util.Optional;
import java.util.regex.Pattern;

@Service
public class AnimeService {

    private static final Pattern SQL_INJECTION_PATTERN = Pattern.compile(
            "(--|/\\*|\\*/|\\bOR\\b\\s+1\\s*=\\s*1|;\\s*(ALTER|DROP|INSERT|DELETE|UPDATE|SELECT|GRANT|REVOKE|UNION|CREATE)\\b|\\b(ALTER|DROP|INSERT|DELETE|UPDATE|SELECT|GRANT|REVOKE|UNION|CREATE)\\b)",
            Pattern.CASE_INSENSITIVE
    );
    private static final Pattern WHITESPACE_PATTERN = Pattern.compile("\\s+");

    private final AnimeRepository repository;

    public AnimeService(AnimeRepository repository) {
        this.repository = repository;
    }

    public List<AnimeModel> findAll(){
        return repository.findAll();
    }

    public Optional<AnimeModel> findById(Long id){
        return repository.findById(id);
    }

    public Optional<AnimeModel> findByTitulo(String titulo){
        String sanitizedTitulo = sanitizeTitulo(titulo);
        return repository.findByTitulo(sanitizedTitulo);
    }

    public List<AnimeModel> findByCategoria(Categoria categoria){
        return repository.findByCategoria(categoria);
    }

    public AnimeModel save(AnimeModel animeModel){
        sanitizeAnime(animeModel);
        return repository.save(animeModel);
    }

    public Optional<AnimeModel> updateById(Long id, AnimeModel animeAtualizado) {
        sanitizeAnime(animeAtualizado);
        return repository.findById(id)
                .map(anime -> {
                    anime.setTitulo(animeAtualizado.getTitulo());
                    anime.setCategoria(animeAtualizado.getCategoria());
                    anime.setAnoLancamento(animeAtualizado.getAnoLancamento());
                    anime.setNumEpisodios(animeAtualizado.getNumEpisodios());
                    return repository.save(anime);
                });
    }

    public boolean deleteById(Long id) {
        if (repository.existsById(id)) {
            repository.deleteById(id);
            return true;
        }
        return false;
    }

    private void sanitizeAnime(AnimeModel animeModel) {
        if (animeModel == null) {
            throw new IllegalArgumentException("Os dados do anime são obrigatórios.");
        }
        animeModel.setTitulo(sanitizeTitulo(animeModel.getTitulo()));
    }

    private String sanitizeTitulo(String rawTitulo) {
        if (rawTitulo == null) {
            throw new IllegalArgumentException("O título é obrigatório.");
        }

        String normalized = Normalizer.normalize(rawTitulo, Normalizer.Form.NFC).trim();
        if (normalized.isEmpty()) {
            throw new IllegalArgumentException("O título é obrigatório.");
        }

        normalized = WHITESPACE_PATTERN.matcher(normalized).replaceAll(" ");

        if (SQL_INJECTION_PATTERN.matcher(normalized).find()) {
            throw new IllegalArgumentException("O título contém termos potencialmente maliciosos.");
        }

        return normalized;
    }
}

