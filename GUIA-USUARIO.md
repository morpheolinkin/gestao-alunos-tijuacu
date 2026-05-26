# Guia do Usuário - Sistema de Gestão de Alunos

Este guia mostra, com exemplos visuais, como usar o sistema no dia a dia e como interpretar o **dashboard**.

---

## 1. Tela inicial / Login

> *(INSERIR PRINT DA TELA DE LOGIN OU HOME AQUI)*

- Acesse o endereço fornecido pela equipe de TI (por exemplo: `http://localhost:8080` ou um domínio interno).
- Informe seu usuário e senha (se o sistema tiver autenticação configurada).
- Após o login, você verá o menu principal com as opções de **Alunos**, **Turmas**, **Matrículas** e **Dashboard**.

---

## 2. Módulo Alunos

> *(INSERIR PRINT DA LISTAGEM DE ALUNOS)*

Nesta tela você pode:

- Ver a lista de alunos cadastrados.
- Buscar por nome.
- Filtrar por sexo, transporte, AEE (se o frontend tiver filtros).
- Cadastrar um novo aluno.

### 2.1. Cadastro de aluno

> *(INSERIR PRINT DO FORMULÁRIO DE CADASTRO DE ALUNO)*

Campos principais:

- **Nome completo:** nome do estudante.
- **Sexo:** masculino/feminino.
- **Transporte escolar:** se usa transporte municipal, estadual ou nenhum.
- **Tipo AEE:** se recebe atendimento educacional especializado.
- **Ativo:** indica se o aluno está ativo no sistema.

Essas informações alimentam gráficos como:

- Alunos por sexo.
- Alunos por transporte escolar.
- Alunos com/sem AEE.

---

## 3. Módulo Turmas

> *(INSERIR PRINT DA LISTAGEM DE TURMAS)*

Aqui você gerencia as turmas da escola:

- Cada linha representa uma turma (ex.: “6º Ano A”).
- Informações típicas:
  - Nome da turma.
  - Ano letivo.
  - Modalidade (REGULAR, EPJAI, etc.).

### 3.1. Cadastro de turma

> *(INSERIR PRINT DO FORMULÁRIO DE CADASTRO DE TURMA)*

Ao criar uma turma, pense:

- Em qual **ano letivo** ela existe.
- Qual modalidade (por exemplo, ensino regular).

Isso é importante porque as matrículas e os indicadores do dashboard são filtrados por ano letivo.

---

## 4. Módulo Matrículas

> *(INSERIR PRINT DA LISTAGEM DE MATRÍCULAS)*

As matrículas ligam **alunos** às **turmas** em um determinado ano.

Nesta tela você pode:

- Ver todas as matrículas de um ano letivo.
- Filtrar por turma ou aluno (dependendo dos filtros disponíveis).
- Cadastrar/atualizar matrículas.

### 4.1. Cadastro / Edição de matrícula

> *(INSERIR PRINT DO FORMULÁRIO DE MATRÍCULA)*

Campos importantes:

- **Aluno:** escolha um aluno da lista.
- **Turma:** escolha a turma em que ele será matriculado.
- **Ano letivo:** ano da matrícula (ex.: 2026).
- **Situação:**
  - MATRICULADO
  - APROVADO
  - CONSERVADO
  - EVADIDO
  - TRANSFERIDO
  - FALECIDO
- **Datas:**
  - Data de entrada.
  - Data de saída (quando houver).
- **Motivo de saída:** obrigatório para situações como EVADIDO/TRANSFERIDO.

Essas informações impactam diretamente os indicadores do dashboard (aprovações, conservações, evasões, transferências etc.).

---

## 5. Dashboard de indicadores

> *(INSERIR PRINT DA TELA COMPLETA DO DASHBOARD)*

O dashboard mostra um resumo visual da situação dos alunos e matrículas.  
Geralmente é composto por:

- Filtro de **ano letivo**.
- Cards (números grandes).
- Gráficos (barras, linhas, pizza).

### 5.1. Filtro de ano letivo

> *(INSERIR PRINT DO FILTRO DE ANO LETIVO)*

- Selecione o ano (ex.: 2026).
- O sistema recalcula os números para o ano escolhido.
- Isso permite comparar anos diferentes (por exemplo, evasão em 2025 vs 2026).

---

### 5.2. Cards principais

> *(INSERIR PRINT DOS CARDS SUPERIORES DO DASHBOARD)*

Exemplos de cards:

- **Total de alunos ativos:** quantos alunos estão marcados como ativos.
- **Total de matrículas no ano:** quantas matrículas existem no ano letivo selecionado.

Esses números ajudam a ter uma visão rápida do tamanho da rede/turma naquele ano.

---

### 5.3. Situação das matrículas no ano

> *(INSERIR PRINT DO GRÁFICO DE SITUAÇÃO DAS MATRÍCULAS)*

Gráfico típico: barras ou pizza com:

- Matriculados
- Aprovados
- Conservados
- Evadidos
- Transferidos
- Falecidos

Como usar:

- Observe quantos alunos foram aprovados e quantos ficaram conservados/reprovados.
- Verifique o número de evasões e transferências no ano para identificar se há aumento ou redução em relação a anos anteriores.

---

### 5.4. Evasões e transferências por mês

> *(INSERIR PRINT DO GRÁFICO DE EVASÕES/TRANSFERÊNCIAS POR MÊS)*

Esse gráfico mostra:

- No eixo horizontal: meses (jan, fev, mar, ...)
- No eixo vertical: quantidade de alunos que tiveram a situação alterada para **EVADIDO** ou **TRANSFERIDO**.

Como interpretar:

- Picos em determinados meses indicam períodos críticos de evasão ou transferência.
- É útil para planejar ações específicas (busca ativa, reuniões com famílias, etc.) nesses períodos.

---

### 5.5. Histórico de situações (todos os anos)

> *(INSERIR PRINT DO GRÁFICO DE SITUAÇÃO HISTÓRICA)*

Esse gráfico mostra um apanhado geral:

- Quantos alunos já foram aprovados, conservados, evadidos, transferidos, etc., considerando todos os anos.

Ajuda a entender tendências de longo prazo.

---

### 5.6. Perfil dos alunos

> *(INSERIR PRINT DOS GRÁFICOS DE PERFIL – SEXO, TRANSPORTE, AEE)*

Gráficos comuns:

- **Por sexo:** distribuição entre alunos masculinos e femininos.
- **Por transporte escolar:** quantos usam transporte municipal, estadual ou nenhum.
- **Por AEE:** quantos possuem atendimento educacional especializado.

Essas informações ajudam na alocação de recursos (transporte, salas de recursos, profissionais de apoio, etc.).

---

### 5.7. Alunos por turma

> *(INSERIR PRINT DO GRÁFICO DE ALUNOS POR TURMA)*

Mostra, para o ano letivo selecionado:

- Cada turma (ex.: “6º Ano A”, “7º Ano B”) com a quantidade de alunos.

Como usar:

- Identificar turmas superlotadas.
- Ver turmas com poucos alunos.
- Equilibrar turmas em novos planejamentos.

---

## 6. Boas práticas de uso

- Mantenha **alunos**, **turmas** e **matrículas** sempre atualizados.
- Atualize a **situação da matrícula** assim que houver aprovação, conservação, evasão ou transferência.
- Use o **filtro de ano letivo** para:
  - Acompanhar o ano corrente.
  - Comparar com anos anteriores.

---

## 7. Ajuda e suporte

Em caso de dúvidas:

- Entre em contato com a equipe de TI ou com o responsável pelo sistema na escola/secretaria.
- Em problemas técnicos, informe:
  - O que você tentou fazer.
  - A tela onde estava.
  - A mensagem de erro (se aparecer).
