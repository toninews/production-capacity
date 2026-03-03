const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

export async function fetchPossibleProduction() {
  const response = await fetch(`${API_BASE_URL}/production-capacity`);

  if (!response.ok) {
    throw new Error('Não foi possível carregar a produção.');
  }

  return response.json();
}
