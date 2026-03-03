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
            productId: 10,
            productName: 'Produto A',
            producibleQuantity: 7,
            limitingFactor: {
              name: 'Parafuso',
              availableQuantity: 7,
              consumptionPerUnit: 1
            }
          }
        ]
      })
    );

    render(<App />);

    expect(screen.getByText('Carregando produção...')).toBeInTheDocument();
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
      'Não foi possível carregar a produção.'
    );
  });

  it('filters the list by product name', async () => {
    vi.stubGlobal(
      'fetch',
      vi.fn().mockResolvedValue({
        ok: true,
        json: async () => [
          { productId: 10, productName: 'Produto A', producibleQuantity: 7, limitingFactor: null },
          { productId: 20, productName: 'Produto B', producibleQuantity: 5, limitingFactor: null }
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
            productId: 10,
            productName: 'Produto A',
            producibleQuantity: 7,
            limitingFactor: { name: 'Parafuso', availableQuantity: 7, consumptionPerUnit: 1 }
          },
          {
            productId: 40,
            productName: 'Produto D',
            producibleQuantity: 0,
            limitingFactor: { name: 'Cobre', availableQuantity: 0, consumptionPerUnit: 2 }
          }
        ]
      })
    );

    render(<App />);

    await screen.findByText('Produto A');

    fireEvent.click(screen.getByLabelText('Somente indisponíveis'));

    await waitFor(() => {
      expect(screen.queryByText('Produto A')).not.toBeInTheDocument();
    });

    expect(screen.getByText('Produto D')).toBeInTheDocument();
    expect(screen.getByText('Sem estoque suficiente de Cobre')).toBeInTheDocument();
  });
});
