# 🎌 AnimeAI 🤖

AnimeAI é uma aplicação Spring Boot que combina uma API REST, páginas Thymeleaf e integrações reativas com a OpenAI para ajudar você a gerenciar um catálogo de animes e descobrir novos títulos parecidos com os que já assistiu. Os dados são persistidos em um banco H2 versionado com Flyway.

## 🧭 Sumário
- [🔎 Visão Geral](#-visão-geral)
- [🧱 Stack & Arquitetura](#-stack--arquitetura)
- [✨ Funcionalidades](#-funcionalidades)
- [🗂️ Estrutura do Projeto](#️-estrutura-do-projeto)
- [⚙️ Configuração do Ambiente](#️-configuração-do-ambiente)
- [▶️ Executando o Projeto](#️-executando-o-projeto)
- [🔐 Variáveis de Ambiente](#-variáveis-de-ambiente)
- [🛰️ Endpoints REST](#️-endpoints-rest)
- [🖥️ Interface Web](#️-interface-web)
- [🧪 Testes](#-testes)
- [🚀 Próximos Passos](#-próximos-passos)
- [🤝 Como Contribuir](#-como-contribuir)

## 🔎 Visão Geral
- API RESTful para CRUD de animes com validação no serviço.
- Interface web (Thymeleaf) para cadastrar, editar e remover animes de forma amigável.
- Integração com a OpenAI usando WebFlux para gerar recomendações baseadas no catálogo atual.
- Banco H2 em modo arquivo (`./data/anime-db`) com migrações Flyway para garantir a criação das tabelas.

## 🧱 Stack & Arquitetura
- ☕ **Java 17** + **Spring Boot 3.5.6**.
- 🌐 **Spring Web** para os endpoints REST e **Spring WebFlux** para chamadas reativas à OpenAI.
- 🗃️ **Spring Data JPA** com banco **H2** (configurado para modo PostgreSQL compatibility).
- 🛫 **Flyway** controla as versões do banco.
- 🖼️ **Thymeleaf** renderiza as páginas HTML.
- 🧰 **Lombok** reduz boilerplate nas entidades.
- 🌱 **spring-dotenv** permite carregar variáveis a partir de um `.env`.

## ✨ Funcionalidades
- 📚 **CRUD completo de animes** com título, categoria, ano de lançamento e número de episódios.
- 🧠 **Sugestões automáticas** via OpenAI com o modelo `gpt-4o-mini`, apresentadas tanto na API quanto na interface.
- 🗄️ **Migrações Flyway** garantem que o esquema da tabela `anime` esteja sempre atualizado.
- 📊 **Console H2** acessível em `/h2` para depuração rápida (usuário `sa`, senha configurada no `application.properties`).
- 🖥️ **UI responsiva** construída com HTML + CSS customizado para facilitar o gerenciamento do catálogo.

## 🗂️ Estrutura do Projeto
```text
AnimeAI/
├── src/main/java/com/example/AnimeAI/
│   ├── AnimeAiApplication.java   # Classe principal do Spring Boot
│   ├── config/                   # Beans de configuração (WebClient para OpenAI)
│   ├── controller/               # Controllers REST e Web (Thymeleaf)
│   ├── model/                    # Entidades JPA e enums de domínio
│   ├── repository/               # Interfaces Spring Data
│   └── service/                  # Regras de negócio e integrações externas
├── src/main/resources/
│   ├── application.properties    # Configurações do Spring Boot e da base H2
│   ├── db/migration/             # Scripts Flyway (V1__create_anime.sql.sql)
│   ├── templates/animes/         # Páginas Thymeleaf (lista, formulário, sugestão)
│   └── static/css/               # Estilos usados na UI
└── data/                         # Arquivos gerados pelo banco H2 no modo file
```

## ⚙️ Configuração do Ambiente
1. 📥 **Clone o repositório**
   ```bash
   git clone https://github.com/seu-usuario/AnimeAI.git
   cd AnimeAI
   ```
2. ☕ **Garanta o Java 17** instalado e configurado (`java -version`).
3. 🔑 **Configure a chave da OpenAI** (veja [Variáveis de Ambiente](#-variáveis-de-ambiente)).
4. 🧪 **Opcional:** ajuste o datasource ou credenciais no `application.properties` caso deseje outro banco.

## ▶️ Executando o Projeto
```bash
./mvnw spring-boot:run
```

Depois que a aplicação subir:
- Acesse `http://localhost:8080/animes` para a interface web.
- A API REST estará disponível sob `http://localhost:8080/anime`.
- O console do H2 pode ser aberto em `http://localhost:8080/h2` (JDBC URL: `jdbc:h2:file:./data/anime-db`).

Para encerrar, pressione `Ctrl + C` no terminal.

## 🔐 Variáveis de Ambiente
O serviço que consulta a OpenAI depende da variável `OPENAI_API_KEY`.

```bash
export OPENAI_API_KEY="sua-chave-aqui"         # macOS/Linux
setx OPENAI_API_KEY "sua-chave-aqui"          # Windows (cmd)
$Env:OPENAI_API_KEY = "sua-chave-aqui"        # Windows PowerShell
```

Alternativamente, crie um arquivo `.env` na raiz do projeto com o conteúdo:

```
OPENAI_API_KEY=sua-chave-aqui
```

> ⚠️ Sem a chave, os endpoints de sugestão continuarão respondendo, mas retornarão mensagens de erro ao tentar acessar a OpenAI.

## 🛰️ Endpoints REST
| Método | Caminho | Descrição |
|--------|---------|-----------|
| `GET` | `/anime` | Lista todos os animes cadastrados. |
| `GET` | `/anime/{id}` | Busca um anime pelo `id`. |
| `GET` | `/anime/titulo/{titulo}` | Recupera um anime pelo título exato. |
| `GET` | `/anime/categoria/{categoria}` | Filtra animes por categoria (`SHOUNEN`, `SHOUJO`, `SEINEN`, `JOSEI`). |
| `POST` | `/anime` | Cadastra um novo anime (JSON no corpo). |
| `PUT` | `/anime/{id}` | Atualiza os dados de um anime existente. |
| `DELETE` | `/anime/{id}` | Remove um anime do catálogo. |
| `GET` | `/suggestion` | Retorna recomendações em texto plano, geradas pela OpenAI. |

### 📤 Exemplo de requisição `POST /anime`
```http
POST /anime HTTP/1.1
Content-Type: application/json

{
  "titulo": "Fullmetal Alchemist: Brotherhood",
  "categoria": "SHOUNEN",
  "anoLancamento": "2009-04-05",
  "numEpisodios": 64
}
```

### 🤖 Exemplo de retorno `/suggestion`
```text
1. Assista "Hunter x Hunter" se você curtiu animes de aventura e estratégia.
2. "Demon Slayer" entrega ação frenética e estética impecável.
```
*(O resultado final depende do catálogo cadastrado e da resposta do modelo da OpenAI.)*

## 🖥️ Interface Web
- `/animes` — lista paginada com alertas de sucesso/erro e ações para editar/excluir.
- `/animes/new` — formulário para cadastrar novos animes.
- `/animes/{id}/edit` — formulário para editar registros existentes.
- `/animes/suggestion` — página que exibe o texto retornado pela OpenAI formatado para leitura.

Os estilos em `static/css/styles.css` garantem uma experiência responsiva tanto em desktop quanto em dispositivos móveis.

## 🧪 Testes
Execute os testes de unidade e integração com Maven:
```bash
./mvnw test
```

## 🚀 Próximos Passos
- 🔐 Autenticação para restringir o catálogo por usuário.
- 🌍 Internacionalização das respostas e da interface.
- 📈 Monitoramento com Spring Boot Actuator e dashboards.

## 🤝 Como Contribuir
1. Faça um fork e crie uma branch feature (`git checkout -b feature/minha-feature`).
2. Implemente suas melhorias e garanta que os testes passam.
3. Abra um Pull Request descrevendo o que mudou. 💬

Feito com muita paixão por anime e tecnologia!
