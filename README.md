# 🎌 AnimeAI 🤖

AnimeAI é uma API Spring Boot que cadastra animes no seu catálogo e consulta a API da OpenAI para sugerir novos títulos semelhantes ao que você já assistiu. O projeto usa H2 + Flyway para persistência e WebFlux para integrações reativas.

## 🧭 Sumário
- [✨ Funcionalidades](#-funcionalidades)
- [🧱 Arquitetura e Stack](#-arquitetura-e-stack)
- [🗂️ Estrutura de Pastas](#️-estrutura-de-pastas)
- [⚙️ Configuração do Ambiente](#️-configuração-do-ambiente)
- [🛰️ Endpoints Principais](#️-endpoints-principais)
- [🧪 Testes](#-testes)
- [🚀 Próximos Passos](#-próximos-passos)
- [🤝 Como Contribuir](#-como-contribuir)

## ✨ Funcionalidades
- 📚 **CRUD completo de animes** com título, categoria, data de lançamento e número de episódios.
- 🧠 **Sugestões automáticas** de novos animes usando o endpoint `/suggestion`, alimentado pelo modelo `gpt-4o-mini` via OpenAI.
- 🗄️ **Migrações Flyway** garantem a criação da tabela `anime` em qualquer ambiente.
- 📊 **Console H2** disponível em `/h2` para inspecionar o banco localmente.

## 🧱 Arquitetura e Stack
- ☕ Java 17 + Spring Boot 3.
- 🌐 Spring Web + WebFlux para APIs REST e chamadas reativas à OpenAI.
- 🗃️ Spring Data JPA com banco H2 em modo file, pronto para PostgreSQL.
- 🛫 Flyway para versionamento do banco.
- 🧰 Lombok para reduzir boilerplate nos modelos.

## 🗂️ Estrutura de Pastas
```text
AnimeAI/
├── src/main/java/com/example/AnimeAI/
│   ├── config/        # Configuração do WebClient para OpenAI
│   ├── controller/    # Endpoints REST (catálogo e sugestões)
│   ├── model/         # Entidades JPA e enum de categorias
│   ├── repository/    # Interface Spring Data JPA
│   └── service/       # Regras de negócio e integração com OpenAI
├── src/main/resources/
│   ├── application.properties   # Configurações do Spring Boot
│   └── db/migration/            # Scripts Flyway
└── data/                        # Arquivos gerados pelo H2 (persistidos em disco)
```

## ⚙️ Configuração do Ambiente
1. 📥 **Clone o repositório**
   ```bash
   git clone https://github.com/seu-usuario/AnimeAI.git
   cd AnimeAI
   ```
2. 🔑 **Configure a chave da OpenAI**
   - Exporte a variável de ambiente antes de subir a aplicação:
     ```bash
     export OPENAI_API_KEY="sua-chave-aqui"
     ```
   - No Windows (PowerShell):
     ```powershell
     $Env:OPENAI_API_KEY = "sua-chave-aqui"
     ```
3. 🧪 **Opcional: ajuste o datasource** em `src/main/resources/application.properties` caso deseje outro banco.
4. ▶️ **Suba a aplicação**
   ```bash
   ./mvnw spring-boot:run
   ```
5. 🌐 **Acesse** `http://localhost:8080` e utilize os endpoints listados abaixo.

> 💡 O projeto usa `spring-dotenv`, então você também pode criar um arquivo `.env` com `OPENAI_API_KEY=...` na raiz.

## 🛰️ Endpoints Principais
| Método | Caminho | Descrição |
|--------|---------|-----------|
| `GET` | `/anime` | Lista todos os animes cadastrados. |
| `GET` | `/anime/{id}` | Busca um anime pelo `id`. |
| `GET` | `/anime/titulo/{titulo}` | Recupera um anime pelo título exato. |
| `GET` | `/anime/categoria/{categoria}` | Filtra animes por categoria (`SHOUNEN`, `SHOUJO`, `SEINEN`, `JOSEI`). |
| `POST` | `/anime` | Cadastra um novo anime (JSON no corpo). |
| `PUT` | `/anime/{id}` | Atualiza um anime existente. |
| `DELETE` | `/anime/{id}` | Remove um anime do catálogo. |
| `GET` | `/suggestion` | Retorna sugestões em texto plano vindas da OpenAI. |

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
*(A resposta final depende dos dados cadastrados e do modelo da OpenAI.)*

## 🧪 Testes
Execute os testes de unidade e integração com Maven:
```bash
./mvnw test
```

## 🚀 Próximos Passos
- 🔐 Autenticação para restringir o catálogo por usuário.
- 🌍 Internacionalização das respostas.
- 📈 Monitoramento com Actuator e dashboards.

## 🤝 Como Contribuir
1. Faça um fork e crie uma branch feature (`git checkout -b feature/minha-feature`).
2. Implemente suas melhorias e garanta que os testes passam.
3. Abra um Pull Request descrevendo o que mudou. 💬

Feito com muita paixão por anime e tecnologia! 
