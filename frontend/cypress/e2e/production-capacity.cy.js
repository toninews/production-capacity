describe('Production capacity dashboard', () => {
  it('renders the possible production table', () => {
    cy.intercept('GET', '**/api/producao/possivel', {
      statusCode: 200,
      body: [
        {
          produtoId: 10,
          nome: 'Produto A',
          quantidadeProduzivel: 7,
          limitante: {
            materiaPrimaId: 2,
            nome: 'Parafuso',
            disponivel: 7,
            consumoPorUnidade: 1
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
