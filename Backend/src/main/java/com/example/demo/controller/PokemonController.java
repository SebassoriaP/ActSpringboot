package com.example.demo.controller;

import com.example.demo.data.PokemonSeedData;
import com.example.demo.dto.BattleRequest;
import com.example.demo.model.Pokemon;
import com.example.demo.services.BattleService;
import com.example.demo.services.PokemonService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

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
        Pokemon p1 = pokemonService.getPokemonById(request.getPokemon1Id());
        Pokemon p2 = pokemonService.getPokemonById(request.getPokemon2Id());

        if (p1 == null || p2 == null) {
            return ResponseEntity.badRequest().body("Uno o ambos Pokémon no existen");
        }

        Pokemon winner = battleService.fight(p1, p2);

        return ResponseEntity.ok(Map.of(
                "pokemon1", p1,
                "pokemon2", p2,
                "winner", winner
        ));
    }
}