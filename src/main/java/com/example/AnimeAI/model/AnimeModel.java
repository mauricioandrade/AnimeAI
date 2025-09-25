package com.example.AnimeAI.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Pattern;
import jakarta.validation.constraints.PastOrPresent;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.format.annotation.DateTimeFormat;

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
    @Column(nullable = false, length = 120)
    @NotBlank(message = "O título é obrigatório.")
    @Size(max = 120, message = "O título deve ter no máximo {max} caracteres.")
    @Pattern(
            regexp = """
            ^[\\p{L}\\p{N}\\s\\-'.:,;!?()]+$
            """,
            message = "O título contém caracteres inválidos."
    )
    private String titulo;
    @Enumerated(EnumType.STRING)
    private Categoria categoria;
    @Column(name = "ano_lancamento")
    @DateTimeFormat(pattern = "yyyy-MM-dd")
    @PastOrPresent(message = "O ano de lançamento deve ser uma data válida no passado ou presente.")
    private LocalDate anoLancamento;
    @Column(name = "num_episodios")
    @Positive(message = "O número de episódios deve ser maior que zero.")
    private Integer numEpisodios;

    @PrePersist
    @PreUpdate
    private void normalizeFields() {
        if (titulo != null) {
            titulo = titulo.trim();
        }
    }

}
