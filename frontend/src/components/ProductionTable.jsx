function getCapacityStatus(item) {
  if (!item.limitingFactor) {
    return 'Sem receita';
  }

  if (item.producibleQuantity === 0) {
    return `Sem estoque suficiente de ${item.limitingFactor.name}`;
  }

  return `${item.limitingFactor.availableQuantity} disponivel / ${item.limitingFactor.consumptionPerUnit} por unidade`;
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
            <tr key={item.productId}>
              <td>{item.productName}</td>
              <td>
                <strong>{item.producibleQuantity}</strong>
              </td>
              <td>
                {item.limitingFactor ? (
                  <div className="limiting-factor-cell">
                    <span className="limiting-factor-badge">
                      Limitado por: {item.limitingFactor.name}
                    </span>
                    <span className="limiting-factor-detail">{getCapacityStatus(item)}</span>
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
