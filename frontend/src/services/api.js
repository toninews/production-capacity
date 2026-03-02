const API_BASE_URL = import.meta.env.VITE_API_BASE_URL || 'http://localhost:8080/api';

export async function fetchPossibleProduction() {
  const response = await fetch(`${API_BASE_URL}/production-capacity`);

  if (!response.ok) {
    throw new Error('Nao foi possivel carregar a producao.');
  }

  return response.json();
}
