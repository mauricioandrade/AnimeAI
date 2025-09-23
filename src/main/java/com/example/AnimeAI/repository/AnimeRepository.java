package com.example.AnimeAI.repository;

import com.example.AnimeAI.model.AnimeModel;
import com.example.AnimeAI.model.Categoria;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Optional;

@Repository
public interface AnimeRepository extends JpaRepository<AnimeModel, Long> {

    Optional<AnimeModel> findByTitulo(String titulo);

    List<AnimeModel> findByCategoria(Categoria categoria);

}
