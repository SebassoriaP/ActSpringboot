export type Pokemon = {
  id: string;
  name: string;
  type: string;
  hp: number;
  attack: number;
  defense: number;
  abilities: string[];
  powers: string[];
  imageUrl: string;
};

export type BattleResponse = {
  pokemon1: Pokemon;
  pokemon2: Pokemon;
  winner: Pokemon;
};