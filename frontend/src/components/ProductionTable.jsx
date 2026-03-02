function getCapacityStatus(item) {
  if (!item.limitante) {
    return 'Sem receita';
  }

  if (item.quantidadeProduzivel === 0) {
    return `Sem estoque suficiente de ${item.limitante.nome}`;
  }

  return `${item.limitante.disponivel} disponivel / ${item.limitante.consumoPorUnidade} por unidade`;
}

export function ProductionTable({ items }) {
  return (
    <div className="table-shell">
      <table>
        <thead>
          <tr>
            <th>Produto</th>
            <th>Quantidade Produzivel</th>
            <th>Limitante</th>
          </tr>
        </thead>
        <tbody>
          {items.map((item) => (
            <tr key={item.produtoId}>
              <td>{item.nome}</td>
              <td>
                <strong>{item.quantidadeProduzivel}</strong>
              </td>
              <td>
                {item.limitante ? (
                  <div className="limitante-cell">
                    <span className="limitante-badge">
                      Limitado por: {item.limitante.nome}
                    </span>
                    <span className="limitante-detail">{getCapacityStatus(item)}</span>
                  </div>
                ) : (
                  'Sem receita'
                )}
              </td>
            </tr>
          ))}
        </tbody>
      </table>
    </div>
  );
}
