package com.example.AnimeAI.config;

import io.swagger.v3.oas.models.ExternalDocumentation;
import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class OpenApiConfig {

    @Bean
    public OpenAPI animeAiOpenAPI() {
        return new OpenAPI()
                .info(new Info()
                        .title("AnimeAI API")
                        .description("API para gerenciar animes e obter sugestões alimentadas pela OpenAI.")
                        .version("v1")
                        .contact(new Contact()
                                .name("AnimeAI Team")
                                .url("https://github.com")
                                .email("contato@animeai.example"))
                        .license(new License()
                                .name("Apache 2.0")
                                .url("https://www.apache.org/licenses/LICENSE-2.0.html")))
                .externalDocs(new ExternalDocumentation()
                        .description("Repositório do projeto")
                        .url("https://github.com/seu-usuario/AnimeAI"));
    }
}

