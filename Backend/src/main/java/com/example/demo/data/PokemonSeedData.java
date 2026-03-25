package com.example.demo.data;

import com.example.demo.model.Pokemon;

import java.util.List;

public class PokemonSeedData {

    public static List<Pokemon> getPokemons() {
        return List.of(
                new Pokemon(
                        "1",
                        "Pikachu",
                        "Electric",
                        35,
                        55,
                        40,
                        List.of("Static", "Lightning Rod"),
                        List.of("Thunderbolt", "Quick Attack", "Iron Tail"),
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/25.png"
                ),
                new Pokemon(
                        "2",
                        "Charizard",
                        "Fire/Flying",
                        78,
                        84,
                        78,
                        List.of("Blaze", "Solar Power"),
                        List.of("Flamethrower", "Fly", "Fire Spin"),
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/6.png"
                ),
                new Pokemon(
                        "3",
                        "Blastoise",
                        "Water",
                        79,
                        83,
                        100,
                        List.of("Torrent", "Rain Dish"),
                        List.of("Hydro Pump", "Water Gun", "Bite"),
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/9.png"
                ),
                new Pokemon(
                        "4",
                        "Venusaur",
                        "Grass/Poison",
                        80,
                        82,
                        83,
                        List.of("Overgrow", "Chlorophyll"),
                        List.of("Vine Whip", "Solar Beam", "Razor Leaf"),
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/3.png"
                ),
                new Pokemon(
                        "5",
                        "Gengar",
                        "Ghost/Poison",
                        60,
                        65,
                        60,
                        List.of("Cursed Body"),
                        List.of("Shadow Ball", "Night Shade", "Hypnosis"),
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/94.png"
                ),
                new Pokemon(
                        "6",
                        "Lucario",
                        "Fighting/Steel",
                        70,
                        110,
                        70,
                        List.of("Steadfast", "Inner Focus"),
                        List.of("Aura Sphere", "Close Combat", "Metal Claw"),
                        "https://raw.githubusercontent.com/PokeAPI/sprites/master/sprites/pokemon/448.png"
                )
        );
    }
}