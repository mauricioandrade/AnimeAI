package com.example.AnimeAI.service;

import com.example.AnimeAI.model.AnimeModel;
import com.example.AnimeAI.model.Categoria;
import com.example.AnimeAI.repository.AnimeRepository;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class AnimeService {

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
        return repository.findByTitulo(titulo);
    }

    public List<AnimeModel> findByCategoria(Categoria categoria){
        return repository.findByCategoria(categoria);
    }

    public AnimeModel save(AnimeModel animeModel){
        return repository.save(animeModel);
    }

    public Optional<AnimeModel> updateById(Long id, AnimeModel animeAtualizado) {
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
}

