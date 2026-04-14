package com.example.demo.controller;

import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.CrossOrigin;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.data.PokemonSeedData;
import com.example.demo.dto.BattleRequest;
import com.example.demo.model.Pokemon;
import com.example.demo.services.BattleService;
import com.example.demo.services.PokemonService;

@RestController
@RequestMapping("/api")
@CrossOrigin(origins = "*")
public class PokemonController {

    private final PokemonService pokemonService;
    private final BattleService battleService;

    public PokemonController(PokemonService pokemonService, BattleService battleService) {
        this.pokemonService = pokemonService;
        this.battleService = battleService;
    }

    @GetMapping("/pokemons")
    public ResponseEntity<List<Pokemon>> getAllPokemons() throws Exception {
        return ResponseEntity.ok(pokemonService.getAllPokemons());
    }

    @GetMapping("/pokemons/{id}")
    public ResponseEntity<Pokemon> getPokemonById(@PathVariable String id) throws Exception {
        Pokemon pokemon = pokemonService.getPokemonById(id);
        if (pokemon == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(pokemon);
    }

    @PostMapping("/pokemons/seed")
    public ResponseEntity<String> seedPokemons() throws Exception {
        pokemonService.seedPokemons(PokemonSeedData.getPokemons());
        return ResponseEntity.ok("6 pokémon cargados correctamente");
    }

    @PostMapping("/battle")
    public ResponseEntity<?> battle(@RequestBody BattleRequest request) throws Exception {
        var result = pokemonService.fightTurn(
            request.getPokemon1Id(),
            request.getPokemon2Id()
        );

        if (result == null) {
            return ResponseEntity.badRequest().body("Pokémon no encontrado");
        }

        return ResponseEntity.ok(result);
    }

    @PostMapping("/attack")
    public ResponseEntity<?> attack(@RequestBody BattleRequest request) throws Exception {
        Pokemon updated = pokemonService.applyDamage(
            request.getPokemon1Id(),
            request.getPokemon2Id()
        );

        if (updated == null) {
            return ResponseEntity.badRequest().body("Pokémon no encontrado");
        }

        return ResponseEntity.ok(updated);
    }

    @PostMapping("/reset")
    public ResponseEntity<?> reset() throws Exception {
        pokemonService.resetAllPokemons(PokemonSeedData.getPokemons());
        return ResponseEntity.ok("Pokémon restaurados");
    }
}