package com.example.demo.dto;

public class BattleRequest {
    private String pokemon1Id;
    private String pokemon2Id;

    public BattleRequest() {}

    public String getPokemon1Id() { return pokemon1Id; }
    public void setPokemon1Id(String pokemon1Id) { this.pokemon1Id = pokemon1Id; }

    public String getPokemon2Id() { return pokemon2Id; }
    public void setPokemon2Id(String pokemon2Id) { this.pokemon2Id = pokemon2Id; }
}