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

`producibleQuantity = min(floor(availableStock / consumptionPerUnit))`

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

`GET /api/production-capacity`

Resposta de exemplo:

```json
[
  {
    "productId": 10,
    "productName": "Produto A",
    "producibleQuantity": 7,
    "limitingFactor": {
      "rawMaterialId": 2,
      "name": "Parafuso",
      "availableQuantity": 7,
      "consumptionPerUnit": 1
    }
  }
]
```

Endpoints auxiliares:

- `GET /api/products`
- `GET /api/raw-material-stocks`

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

## Links

- Live demo: https://production-capacity.vercel.app/
