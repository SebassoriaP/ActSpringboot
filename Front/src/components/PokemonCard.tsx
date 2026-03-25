import type { Pokemon } from "../types/pokemon";

type PokemonCardProps = {
  pokemon: Pokemon;
  isSelected: boolean;
  onSelect: (pokemon: Pokemon) => void;
};

export default function PokemonCard({
  pokemon,
  isSelected,
  onSelect,
}: PokemonCardProps) {
  return (
    <div
      className={`pokemon-card ${isSelected ? "selected" : ""}`}
      onClick={() => onSelect(pokemon)}
    >
      <img
        src={pokemon.imageUrl}
        alt={pokemon.name}
        className="pokemon-image"
      />

      <h3 className="pokemon-name">{pokemon.name}</h3>
      <div className="pokemon-type">{pokemon.type}</div>

      <div className="stats">
        <div className="stat-box">
          <span className="stat-label">HP</span>
          <span className="stat-value">{pokemon.hp}</span>
        </div>
        <div className="stat-box">
          <span className="stat-label">Ataque</span>
          <span className="stat-value">{pokemon.attack}</span>
        </div>
        <div className="stat-box">
          <span className="stat-label">Defensa</span>
          <span className="stat-value">{pokemon.defense}</span>
        </div>
      </div>

      <div className="info-block">
        <div className="info-title">Habilidades</div>
        <div className="info-text">{pokemon.abilities.join(", ")}</div>
      </div>

      <div className="info-block">
        <div className="info-title">Poderes</div>
        <div className="info-text">{pokemon.powers.join(", ")}</div>
      </div>
    </div>
  );
}