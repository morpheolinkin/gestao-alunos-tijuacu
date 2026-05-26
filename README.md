# 📚 Gestão de Alunos - Tijuacu

Sistema de gestão de alunos, turmas e matrículas, com **dashboard de indicadores educacionais** (matrículas, evasões, transferências, aprovações etc.).

> Focado em redes/escolas que precisam acompanhar fluxo escolar e perfil dos alunos.

---

## 🧱 Tecnologias

- **Linguagem:** Java 17+
- **Framework:** Spring Boot (Web, Data JPA, Validation)
- **ORM:** Hibernate (JPA)
- **Banco de dados:** PostgreSQL
- **Build:** Maven
- **Documentação REST:** Springdoc OpenAPI / Swagger UI

A estrutura segue as boas práticas de organização de projetos Spring Boot em camadas (controller, service, repository, model, dto). [web:331][web:333][web:335]

---

## 🗂 Estrutura do projeto (pacotes)

Exemplo de organização (ajuste para o seu pacote real):

```text
br.com.tijuacu.gestaoalunos
├── GestaoAlunosApplication.java
│
├── controller
│   ├── AlunoController.java
│   ├── TurmaController.java
│   ├── MatriculaController.java
│   └── DashboardController.java
│
├── service
│   ├── AlunoService.java
│   ├── TurmaService.java
│   ├── MatriculaService.java
│   └── DashboardService.java
│
├── repository
│   ├── AlunoRepository.java
│   ├── TurmaRepository.java
│   └── MatriculaRepository.java
│
├── model
│   ├── entity
│   │   ├── Aluno.java
│   │   ├── Turma.java
│   │   └── Matricula.java
│   └── enums
│       ├── Sexo.java
│       ├── SituacaoMatricula.java
│       ├── TransporteEscolar.java
│       └── TipoAee.java
│
├── dto
│   ├── request
│   └── response
│       └── DashboardResumoDTO.java
│
└── mapper
    └── MatriculaMapper.java
```

Essa organização facilita a evolução do projeto e segue as recomendações oficiais de estruturação de código Spring Boot. [web:331][web:333][web:335]

---

## ⚙️ Pré-requisitos

- Java JDK 17+
- Maven
- PostgreSQL (instalado localmente ou via Docker)

Opcional para desenvolvimento:

- Docker + Docker Compose (para subir banco rapidamente). [web:319][web:323][web:329]

---

## 🗄 Configurando o banco de dados

### 1) Banco PostgreSQL local

Crie o banco:

```sql
CREATE DATABASE gestao_alunos;
```

Usuário e senha sugeridos (ajuste conforme seu ambiente):

- Usuário: `postgres`
- Senha: `postgres`
- Host: `localhost`
- Porta: `5432`

### 2) Ou usando Docker Compose

Exemplo de `docker-compose.yml` mínimo:

```yaml
version: "3.8"
services:
  db:
    image: postgres:15
    container_name: gestao-alunos-db
    environment:
      POSTGRES_DB: gestao_alunos
      POSTGRES_USER: postgres
      POSTGRES_PASSWORD: postgres
    ports:
      - "5432:5432"
```

Subir o banco:

```bash
docker-compose up -d
```

---

## 🔧 Configuração da aplicação

Arquivo `src/main/resources/application.properties` (exemplo):

```properties
spring.datasource.url=jdbc:postgresql://localhost:5432/gestao_alunos
spring.datasource.username=postgres
spring.datasource.password=postgres

spring.jpa.hibernate.ddl-auto=update
spring.jpa.show-sql=true
spring.jpa.properties.hibernate.format_sql=true
spring.jpa.properties.hibernate.dialect=org.hibernate.dialect.PostgreSQLDialect

server.port=8080
```

> Em produção, recomenda-se trocar `ddl-auto=update` por `validate` e usar migrações (Flyway/Liquibase). [web:321][web:330]

---

## ▶️ Como rodar

### 1) Via Maven

```bash
mvn clean install
mvn spring-boot:run
```

### 2) Via IDE

- Importar como projeto Maven.
- Rodar a classe `GestaoAlunosApplication` como aplicação Spring Boot.

### 3) Acessos principais

- API: `http://localhost:8080/api/...`
- Swagger UI: `http://localhost:8080/swagger-ui.html` ou `/swagger-ui/index.html`

---

## 🧩 Domínio: Entidades principais

### Aluno

- Representa o estudante.
- Campos principais: `id`, `nomeCompleto`, `sexo`, `transporteEscolar`, `tipoAee`, `ativo`, etc.
- Relacionamento:
  - Um `Aluno` pode ter várias `Matricula` (um-para-muitos).

### Turma

- Representa uma turma (ex.: “6º Ano A”).
- Campos principais: `id`, `nome`, `anoLetivo`, `modalidade` etc.
- Relacionamento:
  - Uma `Turma` possui várias `Matricula`.

### Matricula

- Liga um `Aluno` a uma `Turma` em um determinado **ano letivo**.
- Campos principais: `anoLetivo`, `situacao`, `dataEntrada`, `dataSaida`, `motivoSaida`, etc.
- Relações:
  - `@ManyToOne(fetch = FetchType.EAGER) private Aluno aluno;`
  - `@ManyToOne(fetch = FetchType.EAGER) private Turma turma;`

> O uso de `EAGER` nessas relações evita `LazyInitializationException` ao montar DTOs no service/controller. [web:321][web:327][web:330]

---

## 📊 Dashboard (resumo de indicadores)

### DTO: `DashboardResumoDTO`

Inclui:

- `long totalAlunosAtivos`
- `long totalMatriculasNoAnoLetivo`
- `Map<String, Long> alunosPorSexo`
- `Map<String, Long> alunosPorTipoAee`
- `Map<String, Long> alunosPorTransporte`
- `Map<String, Long> matriculasPorSituacaoAno`
- `Map<String, Long> evasoesPorMes`
- `Map<String, Long> transferenciasPorMes`
- `Map<String, Long> matriculasPorSituacaoGeral`
- `Map<Long, Long> alunosPorTurmaNoAno`

### MatriculaRepository (consultas agregadas)

- `countByAnoLetivo(...)`
- `countByAnoLetivoAndSituacao(...)`
- `countBySituacao(...)`
- Query JPQL com `EXTRACT(MONTH FROM m.dataSaida)` para agrupar evasões/transferências por mês.
- Query com `GROUP BY m.turma.id` para contar alunos por turma. [web:214][web:303][web:287]

### DashboardService

- Centraliza a lógica de agregação.
- Usa `AlunoRepository` e `MatriculaRepository` para montar o `DashboardResumoDTO`.
- Converte resultados de `List<Object[]>` em `Map<String, Long>` / `Map<Long, Long>` para consumo direto pelo frontend.

### DashboardController

- Endpoint: `GET /api/dashboard?anoLetivo=2026`
- Retorna `DashboardResumoDTO` com todos os indicadores consolidados.

---

## 🧪 Testes

- Testes de serviço para `MatriculaService` garantem:
  - Mudança correta de situação (EVADIDO, TRANSFERIDO etc.).
  - Atualização de campos como `dataSaida`, `motivoSaida`.
- Cuidados tomados:
  - Evitar `LazyInitializationException` com `fetch = EAGER` nas relações usadas nos mappers ou anotando testes com `@Transactional` quando necessário.

---

## 🧮 Dados de exemplo

Para facilitar uso local:

- Via CRUD: usar endpoints `/api/alunos`, `/api/turmas`, `/api/matriculas`.
- Ou criar `data.sql` com alguns registros iniciais de alunos, turmas e matrículas.

---

## 📌 Roadmap (ideias futuras)

- Autenticação/autorização (perfis: administrador, secretaria, professor).
- Mais filtros no dashboard (por escola, etapa, modalidade).
- Exportação de relatórios (CSV/PDF).
- Integração com outros sistemas da rede.
