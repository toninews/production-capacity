# RF008 - Capacidade de produção

Aplicação full stack para listar quais produtos podem ser produzidos com base nas matérias-primas disponíveis em estoque.

## Estrutura

- `backend/`: Spring Boot 3 + Java 17
- `frontend/`: React + Vite

## Arquitetura

O backend foi organizado em camadas inspiradas em Clean Architecture, separando:

- `domain`: modelos e regra de negócio
- `application`: casos de uso e portas
- `infrastructure`: implementação de persistência em memória
- `presentation`: endpoints e DTOs

Essa organização facilita testes, manutenção e evolução da aplicação.

## Regra de negócio

Para cada produto:

`quantidadeProduzivel = min(floor(estoqueMateriaPrima / consumoPorUnidade))`

Se alguma matéria-prima não existir em estoque, o sistema considera disponibilidade `0`.

## Seed em memória

Matérias-primas em estoque:

- `Aço`: 20
- `Parafuso`: 7
- `Plástico`: 11
- `Borracha`: 2

Produtos:

- `Produto A`: Aço=2, Parafuso=1
- `Produto B`: Aço=3, Plástico=2
- `Produto C`: Parafuso=4, Borracha=1
- `Produto D`: Cobre=2

## Endpoint principal

`GET /api/producao/possivel`

Resposta de exemplo:

```json
[
  {
    "produtoId": 10,
    "nome": "Produto A",
    "quantidadeProduzivel": 7,
    "limitante": {
      "materiaPrimaId": 2,
      "nome": "Parafuso",
      "disponivel": 7,
      "consumoPorUnidade": 1
    }
  }
]
```

Endpoints auxiliares:

- `GET /api/produtos`
- `GET /api/estoque/materias-primas`

## Como rodar

### Backend

Requisitos:

- Java 17
- Maven 3.9+

Comandos:

```bash
cd backend
mvn spring-boot:run
```

Aplicação em `http://localhost:8080`.

### Frontend

Requisitos:

- Node 18+

Comandos:

```bash
cd frontend
npm install
npm run dev
```

Aplicação em `http://localhost:5173`.

Se precisar apontar para outra URL do backend:

```bash
VITE_API_BASE_URL=http://localhost:8080/api npm run dev
```

## Testes

### Backend

```bash
cd backend
mvn test
```

Cobertura prevista:

- teste unitário da regra de cálculo
- teste de integração do endpoint principal com `MockMvc`

### Frontend

```bash
cd frontend
npm install
npm test
```

Cobertura prevista:

- carregamento da lista
- estado de erro
- filtro por nome

### E2E com Cypress

```bash
cd frontend
npm install
npm run test:e2e
```

O teste intercepta a chamada ao endpoint e valida a renderização da tabela.

## Publicação

Para entrega:

1. Inicialize um repositório Git na raiz.
2. Suba para GitHub.
3. Compartilhe o link do repositório.

Se conseguir publicar a aplicação:

- frontend: Vercel ou Netlify
- backend: Render, Railway ou Fly.io
