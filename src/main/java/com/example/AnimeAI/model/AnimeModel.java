package com.example.AnimeAI.model;

import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDate;

@Entity
@Table(name = "anime")
@NoArgsConstructor
@AllArgsConstructor
@Data
public class AnimeModel {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @Column(nullable = false)
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @Column(name = "ano_lancamento")
    private LocalDate anoLancamento;
    @Column(name = "num_episodios")
    private Integer numEpisodios;

}
