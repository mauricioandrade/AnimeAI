package com.example.AnimeAI.controller;

import com.example.AnimeAI.model.AnimeModel;
import com.example.AnimeAI.model.Categoria;
import com.example.AnimeAI.service.AnimeService;
import com.example.AnimeAI.service.OpenAIService;
import jakarta.validation.Valid;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.time.Duration;
import java.util.List;

@Controller
@RequestMapping("/animes")
public class AnimeViewController {

    private final AnimeService animeService;
    private final OpenAIService openAIService;

    public AnimeViewController(AnimeService animeService, OpenAIService openAIService) {
        this.animeService = animeService;
        this.openAIService = openAIService;
    }

    @GetMapping
    public String list(Model model) {
        List<AnimeModel> animes = animeService.findAll();
        model.addAttribute("animes", animes);
        return "animes/list";
    }

    @GetMapping("/new")
    public String showCreateForm(Model model) {
        model.addAttribute("anime", new AnimeModel());
        return "animes/form";
    }

    @PostMapping
    public String create(@Valid @ModelAttribute("anime") AnimeModel anime,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            return "animes/form";
        }

        try {
            animeService.save(anime);
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("titulo", "titulo.invalid", ex.getMessage());
            return "animes/form";
        }

        redirectAttributes.addFlashAttribute("message", "Anime cadastrado com sucesso!");
        return "redirect:/animes";
    }

    @GetMapping("/{id}/edit")
    public String showEditForm(@PathVariable Long id, Model model, RedirectAttributes redirectAttributes) {
        return animeService.findById(id)
                .map(anime -> {
                    model.addAttribute("anime", anime);
                    return "animes/form";
                })
                .orElseGet(() -> {
                    redirectAttributes.addFlashAttribute("errorMessage", "Anime não encontrado.");
                    return "redirect:/animes";
                });
    }

    @PostMapping("/{id}/update")
    public String update(@PathVariable Long id,
                         @Valid @ModelAttribute("anime") AnimeModel anime,
                         BindingResult bindingResult,
                         RedirectAttributes redirectAttributes) {
        if (bindingResult.hasErrors()) {
            anime.setId(id);
            return "animes/form";
        }

        try {
            if (animeService.updateById(id, anime).isPresent()) {
                redirectAttributes.addFlashAttribute("message", "Anime atualizado com sucesso!");
            } else {
                redirectAttributes.addFlashAttribute("errorMessage", "Anime não encontrado.");
            }
        } catch (IllegalArgumentException ex) {
            bindingResult.rejectValue("titulo", "titulo.invalid", ex.getMessage());
            anime.setId(id);
            return "animes/form";
        }

        return "redirect:/animes";
    }

    @PostMapping("/{id}/delete")
    public String delete(@PathVariable Long id, RedirectAttributes redirectAttributes) {
        if (animeService.deleteById(id)) {
            redirectAttributes.addFlashAttribute("message", "Anime removido com sucesso!");
        } else {
            redirectAttributes.addFlashAttribute("errorMessage", "Anime não encontrado.");
        }
        return "redirect:/animes";
    }

    @GetMapping("/suggestion")
    public String suggestion(Model model) {
        String suggestion;
        try {
            suggestion = openAIService.suggestionAnime(animeService.findAll())
                    .block(Duration.ofSeconds(30));
        } catch (Exception e) {
            suggestion = "Erro ao obter sugestão: " + e.getMessage();
        }

        if (suggestion == null || suggestion.isBlank()) {
            suggestion = "Nenhuma sugestão disponível no momento.";
        }

        model.addAttribute("suggestion", suggestion);
        return "animes/suggestion";
    }

    @ModelAttribute("categorias")
    public Categoria[] categorias() {
        return Categoria.values();
    }
}
