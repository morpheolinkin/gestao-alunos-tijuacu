# gestao-alunos

Instruções rápidas para executar o projeto localmente e no Docker.

## 1) Compilar localmente (maven wrapper)
No PowerShell (Windows):

```powershell
& 'C:\Users\AMD7\IdeaProjects\gestao-alunos\mvnw.cmd' -DskipTests package
```

Para rodar direto do Maven (com variáveis de ambiente):

```powershell
$env:APP_JWT_SECRET = "uma-chave-secreta-bem-grande-e-dificil"
$env:APP_JWT_EXPIRATION = "3600000"
& 'C:\Users\AMD7\IdeaProjects\gestao-alunos\mvnw.cmd' spring-boot:run
```

Ou executar o JAR:

```powershell
$env:APP_JWT_SECRET = "uma-chave-secreta-bem-grande-e-dificil"
$env:APP_JWT_EXPIRATION = "3600000"
java -jar target\gestao-alunos-0.0.1-SNAPSHOT.jar
```

Observação: a aplicação usa um banco Postgres por padrão (configurado em `application-dev.yaml`). Você pode levantar o Postgres via Docker Compose (veja abaixo) ou ajustar as configurações para H2 durante desenvolvimento.

## 2) Rodar com Docker Compose (Postgres + app)
Este repositório inclui `docker-compose.yml` e um `Dockerfile`. Para construir as imagens e subir os serviços:

```powershell
# no diretório do projeto
docker-compose up --build
```

Se preferir rodar em background:

```powershell
docker-compose up --build -d
# ver logs do app
docker-compose logs -f app
```

`docker-compose.yml` já configura as variáveis `APP_JWT_SECRET` e `APP_JWT_EXPIRATION` para o container.

## 3) Configurar variáveis de ambiente na IDE (IntelliJ)
- Abra Run/Debug Configurations
- Selecione a configuração da aplicação
- Em "Environment variables" adicione:
  - `APP_JWT_SECRET` = sua-chave-secreta
  - `APP_JWT_EXPIRATION` = 3600000
- Salve e rode a aplicação

## 4) Segurança: não deixar segredos no código
- Em produção, NÃO use valores default hardcoded.
- Utilize variáveis de ambiente, um secret manager (Vault, AWS Secrets Manager) ou arquivos de configuração seguros.

## 5) O que eu alterei neste repositório
- `src/main/resources/application-dev.yaml`: adicionei `app.jwt.secret` e `app.jwt.expiration` com fallback para as variáveis de ambiente.
- `src/main/java/.../JwtTokenProvider.java`: agora lê `app.jwt.secret` e `app.jwt.expiration` (com valores padrão para desenvolvimento).


Se quiser, posso ainda:
- Remover o valor padrão hardcoded (deixando obrigatória a variável em produção) e adicionar validação explicita no startup.
- Gerar um `application-local.yaml` que use H2 para desenvolvimento rápido sem precisar do Postgres.

