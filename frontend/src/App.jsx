import { useEffect, useState } from 'react';
import { ProductionTable } from './components/ProductionTable';
import { fetchPossibleProduction } from './services/api';

function sortItems(items, sortBy) {
  const sorted = [...items];

  if (sortBy === 'quantity-desc') {
    sorted.sort((left, right) => right.producibleQuantity - left.producibleQuantity);
    return sorted;
  }

  if (sortBy === 'quantity-asc') {
    sorted.sort((left, right) => left.producibleQuantity - right.producibleQuantity);
    return sorted;
  }

  sorted.sort((left, right) => left.productName.localeCompare(right.productName));
  return sorted;
}

export default function App() {
  const [items, setItems] = useState([]);
  const [query, setQuery] = useState('');
  const [sortBy, setSortBy] = useState('name');
  const [showUnavailableOnly, setShowUnavailableOnly] = useState(false);
  const [status, setStatus] = useState('loading');
  const [errorMessage, setErrorMessage] = useState('');

  async function loadData() {
    setStatus('loading');
    setErrorMessage('');

    try {
      const data = await fetchPossibleProduction();
      setItems(data);
      setStatus('success');
    } catch (error) {
      setStatus('error');
      setErrorMessage(error instanceof Error ? error.message : 'Erro inesperado.');
    }
  }

  useEffect(() => {
    loadData();
  }, []);

  const filteredItems = sortItems(
    items.filter((item) => {
      const matchesName = item.productName.toLowerCase().includes(query.toLowerCase());
      const matchesAvailability = showUnavailableOnly ? item.producibleQuantity === 0 : true;
      return matchesName && matchesAvailability;
    }),
    sortBy
  );

  const unavailableCount = items.filter((item) => item.producibleQuantity === 0).length;
  const topProduct = items.reduce(
    (best, current) =>
      !best || current.producibleQuantity > best.producibleQuantity ? current : best,
    null
  );

  return (
    <main className="page">
      <section className="hero">
        <p className="eyebrow">RF008</p>
        <h1>Capacidade de producao com base no estoque atual</h1>
        <p className="subtitle">
          Visualize quais produtos podem ser fabricados agora e qual materia-prima
          esta limitando cada item.
        </p>
      </section>

      {status === 'success' && (
        <section className="summary-grid">
          <article className="summary-card">
            <span className="summary-label">Produtos analisados</span>
            <strong>{items.length}</strong>
          </article>
          <article className="summary-card">
            <span className="summary-label">Sem capacidade atual</span>
            <strong>{unavailableCount}</strong>
          </article>
          <article className="summary-card">
            <span className="summary-label">Maior capacidade</span>
            <strong>
              {topProduct ? `${topProduct.productName} (${topProduct.producibleQuantity})` : '-'}
            </strong>
          </article>
        </section>
      )}

      <section className="panel">
        <div className="toolbar">
          <label className="search">
            <span>Filtrar produto</span>
            <input
              type="text"
              placeholder="Ex.: Produto A"
              value={query}
              onChange={(event) => setQuery(event.target.value)}
            />
          </label>
          <label className="filter-select">
            <span>Ordenar por</span>
            <select value={sortBy} onChange={(event) => setSortBy(event.target.value)}>
              <option value="name">Nome</option>
              <option value="quantity-desc">Maior quantidade</option>
              <option value="quantity-asc">Menor quantidade</option>
            </select>
          </label>
          <label className="checkbox-filter">
            <input
              type="checkbox"
              checked={showUnavailableOnly}
              onChange={(event) => setShowUnavailableOnly(event.target.checked)}
            />
            <span>Somente indisponiveis</span>
          </label>
          <button type="button" onClick={loadData}>
            Recarregar
          </button>
        </div>

        {status === 'loading' && <p className="feedback">Carregando producao...</p>}

        {status === 'error' && (
          <div className="feedback feedback-error" role="alert">
            <p>{errorMessage}</p>
            <button type="button" onClick={loadData}>
              Tentar novamente
            </button>
          </div>
        )}

        {status === 'success' && filteredItems.length === 0 && (
          <p className="feedback">Nenhum produto encontrado para o filtro informado.</p>
        )}

        {status === 'success' && filteredItems.length > 0 && (
          <ProductionTable items={filteredItems} />
        )}
      </section>
    </main>
  );
}
