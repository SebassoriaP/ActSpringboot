import type { BattleResponse } from "../types/pokemon";

type BattleResultProps = {
  result: BattleResponse | null;
};

export default function BattleResult({ result }: BattleResultProps) {
  if (!result) return null;

  return (
    <div className="result-card">
      <h2 className="result-title">Resultado de la pelea</h2>
      <div className="result-vs">
        {result.pokemon1.name} vs {result.pokemon2.name}
      </div>
      <div className="result-winner">🏆 Ganador: {result.winner.name}</div>
    </div>
  );
}