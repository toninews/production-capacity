describe('Production capacity dashboard', () => {
  it('renders the possible production table', () => {
    cy.intercept('GET', '**/api/production-capacity', {
      statusCode: 200,
      body: [
        {
          productId: 10,
          productName: 'Produto A',
          producibleQuantity: 7,
          limitingFactor: {
            rawMaterialId: 2,
            name: 'Parafuso',
            availableQuantity: 7,
            consumptionPerUnit: 1
          }
        }
      ]
    }).as('getPossibleProduction');

    cy.visit('/');
    cy.wait('@getPossibleProduction');
    cy.contains('Capacidade de producao com base no estoque atual');
    cy.contains('Produto A');
    cy.contains('7');
  });
});
