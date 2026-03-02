import { cleanup, fireEvent, render, screen, waitFor } from '@testing-library/react';
import { afterEach, describe, expect, it, vi } from 'vitest';
import App from '../App';

describe('App', () => {
  afterEach(() => {
    cleanup();
    vi.restoreAllMocks();
  });

  it('renders products returned by the API', async () => {
    vi.stubGlobal(
      'fetch',
      vi.fn().mockResolvedValue({
        ok: true,
        json: async () => [
          {
            produtoId: 10,
            nome: 'Produto A',
            quantidadeProduzivel: 7,
            limitante: {
              nome: 'Parafuso',
              disponivel: 7,
              consumoPorUnidade: 1
            }
          }
        ]
      })
    );

    render(<App />);

    expect(screen.getByText('Carregando producao...')).toBeInTheDocument();
    expect(await screen.findByText('Produto A')).toBeInTheDocument();
    expect(screen.getByText('7')).toBeInTheDocument();
    expect(screen.getByText('Produtos analisados')).toBeInTheDocument();
    expect(screen.getByText('Maior capacidade')).toBeInTheDocument();
  });

  it('shows an error state when the API call fails', async () => {
    vi.stubGlobal(
      'fetch',
      vi.fn().mockResolvedValue({
        ok: false
      })
    );

    render(<App />);

    expect(await screen.findByRole('alert')).toHaveTextContent(
      'Nao foi possivel carregar a producao.'
    );
  });

  it('filters the list by product name', async () => {
    vi.stubGlobal(
      'fetch',
      vi.fn().mockResolvedValue({
        ok: true,
        json: async () => [
          { produtoId: 10, nome: 'Produto A', quantidadeProduzivel: 7, limitante: null },
          { produtoId: 20, nome: 'Produto B', quantidadeProduzivel: 5, limitante: null }
        ]
      })
    );

    render(<App />);

    await screen.findByText('Produto A');

    fireEvent.change(screen.getByPlaceholderText('Ex.: Produto A'), {
      target: { value: 'B' }
    });

    await waitFor(() => {
      expect(screen.queryByText('Produto A')).not.toBeInTheDocument();
    });

    expect(screen.getByText('Produto B')).toBeInTheDocument();
  });

  it('shows only unavailable products when the filter is active', async () => {
    vi.stubGlobal(
      'fetch',
      vi.fn().mockResolvedValue({
        ok: true,
        json: async () => [
          {
            produtoId: 10,
            nome: 'Produto A',
            quantidadeProduzivel: 7,
            limitante: { nome: 'Parafuso', disponivel: 7, consumoPorUnidade: 1 }
          },
          {
            produtoId: 40,
            nome: 'Produto D',
            quantidadeProduzivel: 0,
            limitante: { nome: 'Cobre', disponivel: 0, consumoPorUnidade: 2 }
          }
        ]
      })
    );

    render(<App />);

    await screen.findByText('Produto A');

    fireEvent.click(screen.getByLabelText('Somente indisponiveis'));

    await waitFor(() => {
      expect(screen.queryByText('Produto A')).not.toBeInTheDocument();
    });

    expect(screen.getByText('Produto D')).toBeInTheDocument();
    expect(screen.getByText('Sem estoque suficiente de Cobre')).toBeInTheDocument();
  });
});
