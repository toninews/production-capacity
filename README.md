# RF008 - Capacidade de producao

Aplicacao full stack para listar quais produtos podem ser produzidos com base nas materias-primas disponiveis em estoque.

## Estrutura

- `backend/`: Spring Boot 3 + Java 17
- `frontend/`: React + Vite

## Arquitetura

O backend foi organizado em camadas inspiradas em Clean Architecture, separando:

- `domain`: modelos e regra de negocio
- `application`: casos de uso e portas
- `infrastructure`: implementacao de persistencia em memoria
- `presentation`: endpoints e DTOs

Essa organizacao facilita testes, manutencao e evolucao da aplicacao.

## Regra de negocio

Para cada produto:

`quantidadeProduzivel = min(floor(estoqueMateriaPrima / consumoPorUnidade))`

Se alguma materia-prima nao existir em estoque, o sistema considera disponibilidade `0`.

## Seed em memoria

Materias-primas em estoque:

- `Aco`: 20
- `Parafuso`: 7
- `Plastico`: 11
- `Borracha`: 2

Produtos:

- `Produto A`: Aco=2, Parafuso=1
- `Produto B`: Aco=3, Plastico=2
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

Aplicacao em `http://localhost:8080`.

### Frontend

Requisitos:

- Node 18+

Comandos:

```bash
cd frontend
npm install
npm run dev
```

Aplicacao em `http://localhost:5173`.

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

- teste unitario da regra de calculo
- teste de integracao do endpoint principal com `MockMvc`

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

O teste intercepta a chamada ao endpoint e valida a renderizacao da tabela.

## Publicacao

Para entrega:

1. Inicialize um repositorio Git na raiz.
2. Suba para GitHub.
3. Compartilhe o link do repositorio.

Se conseguir publicar a aplicacao:

- frontend: Vercel ou Netlify
- backend: Render, Railway ou Fly.io
