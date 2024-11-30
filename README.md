
# Sistema de Reservas de Salas de Reunião

## Descrição
Este projeto é um sistema para gerenciar reservas de salas de reunião. Ele permite que os usuários reservem salas por períodos específicos, visualizem reservas existentes, alterem horários ou cancelem suas reservas. As salas possuem informações como capacidade, recursos disponíveis e status de ativação (ativa ou inativa).

O sistema foi implementado utilizando **Kotlin** com o framework **Spring Boot** e um banco de dados não relacional **MongoDB**. Inclui validações para evitar conflitos de horário e status da sala durante o processo de reserva.

---

## Funcionalidades
1. **Gerenciamento de Salas:**
   - CRUD completo para salas de reunião.
   - Cada sala contém:
     - Nome
     - Capacidade máxima de pessoas
     - Recursos disponíveis (projetor, quadro, etc.)
     - Status (ATIVA/INATIVA)
   - Retorno de status HTTP apropriados em casos de erro (ex.: 404 caso a sala não seja encontrada).

2. **Reserva de Salas:**
   - Reservas podem ser feitas para horários específicos, desde que:
     - A sala esteja ativa.
     - Não existam conflitos de horário com reservas já existentes.
   - Erros tratados:
     - Conflito de horários: **HTTP 400** com mensagem de erro.
     - Sala desativada: **HTTP 400** com mensagem de erro.

3. **Gerenciamento de Reservas:**
   - Visualização de reservas existentes.
   - Alteração de horários de reservas (com validação de disponibilidade).
   - Cancelamento de reservas.
   - Filtros disponíveis para buscar salas por:
     - Data
     - Capacidade
     - Recursos

---

## Tecnologias Utilizadas
- **Linguagem:** Kotlin
- **Framework:** Spring Boot (WebFlux)
- **Banco de Dados:** MongoDB (não-relacional)
- **Gerenciador de Dependências:** Gradle
- **Testes:** JUnit5, Mockito
- **Contêineres:** Docker e Docker Compose

---

## Requisitos de Execução
1. **Pré-requisitos:**
   - Docker e Docker Compose instalados.
   - MongoDB instalado localmente ou configurado em um contêiner.
   - JDK 17 ou superior instalado.

2. **Variáveis de Ambiente:**
   Certifique-se de que as configurações do banco de dados estão definidas no arquivo `application.yml`:
   ```yaml
   spring:
     data:
       mongodb:
         uri: mongodb://localhost:27017/sistemaDeReservas
   ```

---

## Como Executar o Projeto
### Passo 1: Clone o Repositório
```bash
git clone https://github.com/seu-usuario/sistema-de-reservas.git
cd sistema-de-reservas
```

### Passo 2: Construir a Imagem Docker
```bash
docker build -t sistema-reservas .
```

### Passo 3: Subir o Ambiente com Docker Compose
```bash
docker-compose up
```

### Passo 4: Acessar o Sistema
- O sistema estará disponível em: `http://localhost:8080`.

---

## Endpoints Disponíveis

### Gerenciamento de Salas
- **Criar Sala:**  
  `POST /rooms`  
  Corpo da requisição:
  ```json
  {
    "nome": "Sala A",
    "capacidade": 10,
    "recursos": "Projetor, Quadro",
    "status": "ATIVA"
  }
  ```

- **Listar Salas Ativas:**  
  `GET /rooms`

- **Atualizar Sala:**  
  `PUT /rooms/{idRoom}`  
  Corpo da requisição: Mesma estrutura do POST.

- **Deletar Sala:**  
  `DELETE /rooms/{id}`

---

### Gerenciamento de Reservas
- **Criar Reserva:**  
  `POST /reservas`  
  Corpo da requisição:
  ```json
  {
    "salaId": "123",
    "usuario": "João Silva",
    "inicio": "2024-11-23T09:00:00",
    "fim": "2024-11-23T10:00:00"
  }
  ```

- **Listar Reservas Disponíveis:**  
  `GET /reservas/disponiveis`

- **Alterar Reserva:**  
  `PUT /reservas/{id}`  
  Corpo da requisição: Mesma estrutura do POST.

- **Cancelar Reserva:**  
  `DELETE /reservas/{id}`

---

## Testes
- Testes unitários estão implementados com **JUnit 5** e **Mockito**.
- Para executar os testes:
  ```bash
  ./gradlew test
  ```

---

## Cobertura de Testes
O projeto alcança **80% ou mais de cobertura** de código, conforme exigido.

---

## Estrutura do Projeto
```
src/
├── main/
│   ├── kotlin/com/unisales/sistemaDeReservas/
│   │   ├── controller/
│   │   ├── model/
│   │   ├── repository/
│   │   ├── service/
│   │   └── SistemaDeReservasApplication.kt
│   └── resources/
│       ├── application.yml
│       └── static/
└── test/
    └── kotlin/com/unisales/sistemaDeReservas/
```

---

## Contêineres e Docker Compose
O arquivo `docker-compose.yml` já está configurado para subir o sistema e o banco de dados MongoDB. Ele inclui:
- **Aplicação:** Porta `8080`.
- **MongoDB:** Porta `27017`.

---

## Autor
Desenvolvido por **[Seu Nome]**.

---

## Licença
Este projeto está licenciado sob a [MIT License](LICENSE).
