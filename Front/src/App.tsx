import { useEffect, useMemo, useState } from "react";
import "./App.css";
import PokemonCard from "./components/PokemonCard";
import BattleResult from "./components/BattleResult";
import type { BattleResponse, Pokemon } from "./types/pokemon";

const API_URL = "http://localhost:8080/api";

export default function App() {
  const [pokemons, setPokemons] = useState<Pokemon[]>([]);
  const [selectedPokemons, setSelectedPokemons] = useState<Pokemon[]>([]);
  const [battleResult, setBattleResult] = useState<BattleResponse | null>(null);
  const [loading, setLoading] = useState(false);
  const [loadingBattle, setLoadingBattle] = useState(false);
  const [error, setError] = useState("");
  const [lastAttack, setLastAttack] = useState<string>("");

  useEffect(() => {
    const eventSource = new EventSource("http://localhost:8080/api/events");

    eventSource.onmessage = async (event) => {
      console.log("Cambio detectado:", event.data);

      const response = await fetch(`${API_URL}/pokemons`);
      const data = await response.json();
      setPokemons(data);
    };

    return () => {
      eventSource.close();
    };
  }, []);

  useEffect(() => {
    const fetchPokemons = async () => {
      try {
        setLoading(true);
        setError("");

        const response = await fetch(`${API_URL}/pokemons`);
        if (!response.ok) throw new Error("No se pudieron cargar los pokémon");

        const data: Pokemon[] = await response.json();
        setPokemons(data);
      } catch (err) {
        setError(err instanceof Error ? err.message : "Error desconocido");
      } finally {
        setLoading(false);
      }
    };

    fetchPokemons();
  }, []);

  const selectedIds = useMemo(
    () => selectedPokemons.map((pokemon) => pokemon.id),
    [selectedPokemons]
  );

  const handleSelectPokemon = (pokemon: Pokemon) => {
    setBattleResult(null);

    const alreadySelected = selectedPokemons.some((p) => p.id === pokemon.id);

    if (alreadySelected) {
      setSelectedPokemons((prev) => prev.filter((p) => p.id !== pokemon.id));
      return;
    }

    if (selectedPokemons.length >= 2) return;

    setSelectedPokemons((prev) => [...prev, pokemon]);
  };

  const handleBattle = async () => {
    if (selectedPokemons.length !== 2) {
      alert("Selecciona 2 pokémon");
      return;
    }

    try {
      setLoadingBattle(true);
      setError("");

      const response = await fetch(`${API_URL}/battle`, {
        method: "POST",
        headers: {
          "Content-Type": "application/json",
        },
        body: JSON.stringify({
          pokemon1Id: selectedPokemons[0].id,
          pokemon2Id: selectedPokemons[1].id,
        }),
      });

      if (!response.ok) throw new Error("Error en combate");

      const data: BattleResponse = await response.json();

      setBattleResult(data);

      setLastAttack(
        `${data.pokemon1.name} ⚔️ ${data.pokemon2.name} → Ganador: ${data.winner.name}`
      );

    } catch (err) {
      setError(err instanceof Error ? err.message : "Error desconocido");
    } finally {
      setLoadingBattle(false);
    }
  };

  const handleReset = async () => {
    await fetch(`${API_URL}/reset`, { method: "POST" });

    setSelectedPokemons([]);
    setBattleResult(null);
    setLastAttack("");
  };

  return (
    <div className="app">
      <div className="container">
        <h1 className="title">Pokémon Battle</h1>
        <p className="subtitle">
          Selecciona dos pokémon y descubre quién gana la pelea
        </p>

        {loading && <p className="loading">Cargando pokémon...</p>}
        {error && <p className="error">{error}</p>}

        <div className="pokemon-grid">
          {pokemons.map((pokemon) => (
            <PokemonCard
              key={pokemon.id}
              pokemon={pokemon}
              isSelected={selectedIds.includes(pokemon.id)}
              onSelect={handleSelectPokemon}
            />
          ))}
        </div>

        <div className="battle-panel">
          <h2 className="battle-title">Combate</h2>

          <div className="selected-row">
            <div className="selected-card">
              <div className="selected-label">Pokémon 1</div>
              <div className="selected-name">
                {selectedPokemons[0]?.name ?? "No seleccionado"}
              </div>
            </div>

            <div className="selected-card">
              <div className="selected-label">Pokémon 2</div>
              <div className="selected-name">
                {selectedPokemons[1]?.name ?? "No seleccionado"}
              </div>
            </div>
          </div>

          <div className="actions">
            <button
              className="btn-primary"
              onClick={handleBattle}
              disabled={loadingBattle}
            >
              {loadingBattle ? "Combatiendo..." : "⚔️ Combatir"}
            </button>

            <button className="btn-secondary" onClick={handleReset}>
              🔄 Reset
            </button>

            {lastAttack && <p className="attack-log">{lastAttack}</p>}
          </div>

          <BattleResult result={battleResult} />
        </div>
      </div>
    </div>
  );
}