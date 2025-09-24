package com.example.AnimeAI.service;

import com.example.AnimeAI.model.AnimeModel;
import com.example.AnimeAI.model.Categoria;
import com.example.AnimeAI.repository.AnimeRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.LocalDate;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.mockito.Mockito.verify;
import static org.mockito.Mockito.when;

@ExtendWith(MockitoExtension.class)
class AnimeServiceTest {

    @Mock
    private AnimeRepository animeRepository;

    @InjectMocks
    private AnimeService animeService;

    @Test
    void findAllShouldReturnRepositoryResult() {
        List<AnimeModel> expected = List.of(
                new AnimeModel(1L, "Naruto", Categoria.SHOUNEN, LocalDate.of(2002, 10, 3), 220)
        );

        when(animeRepository.findAll()).thenReturn(expected);

        List<AnimeModel> result = animeService.findAll();

        assertEquals(expected, result);
        verify(animeRepository).findAll();
    }
}
