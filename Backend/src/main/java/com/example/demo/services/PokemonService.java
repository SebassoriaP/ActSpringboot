package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.springframework.stereotype.Service;

import com.example.demo.model.Pokemon;
import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.Firestore;
import com.google.cloud.firestore.QueryDocumentSnapshot;
import com.google.cloud.firestore.QuerySnapshot;
import com.google.cloud.firestore.WriteResult;

@Service
public class PokemonService {

    private final Firestore firestore;
    private final String COLLECTION = "pokemons";

    public PokemonService(Firestore firestore) {
        this.firestore = firestore;
    }

    public String savePokemon(Pokemon pokemon) throws Exception {
        ApiFuture<WriteResult> future = firestore
                .collection(COLLECTION)
                .document(pokemon.getId())
                .set(pokemon);

        return future.get().getUpdateTime().toString();
    }

    public List<Pokemon> getAllPokemons() throws Exception {
        ApiFuture<QuerySnapshot> future = firestore.collection(COLLECTION).get();
        List<QueryDocumentSnapshot> documents = future.get().getDocuments();

        List<Pokemon> pokemons = new ArrayList<>();
        for (QueryDocumentSnapshot doc : documents) {
            pokemons.add(doc.toObject(Pokemon.class));
        }

        return pokemons;
    }

    public Pokemon getPokemonById(String id) throws Exception {
        var snapshot = firestore.collection(COLLECTION).document(id).get().get();
        if (!snapshot.exists()) return null;
        return snapshot.toObject(Pokemon.class);
    }

    public List<String> seedPokemons(List<Pokemon> pokemons) throws Exception {
        List<String> results = new ArrayList<>();
        for (Pokemon pokemon : pokemons) {
            pokemon.setHp(pokemon.getMaxHp());
            results.add(savePokemon(pokemon));
        }
        return results;
    }

    public Pokemon applyDamage(String attackerId, String defenderId) throws Exception {
        Pokemon attacker = getPokemonById(attackerId);
        Pokemon defender = getPokemonById(defenderId);

        if (attacker == null || defender == null) return null;

        int newHp = defender.getHp() - attacker.getAttack();
        defender.setHp(Math.max(newHp, 0));

        savePokemon(defender);

        return defender;
    }

    public Map<String, Object> fightTurn(String p1Id, String p2Id) throws Exception {

        Pokemon p1 = getPokemonById(p1Id);
        Pokemon p2 = getPokemonById(p2Id);

        if (p1 == null || p2 == null) return null;

        int hp1 = p1.getHp();
        int hp2 = p2.getHp();

        if (hp1 <= 0 && hp2 <= 0) {
            return Map.of(
                "pokemon1", p1,
                "pokemon2", p2,
                "winner", null,
                "result", "both_fainted"
            );
        }

        if (hp1 > 0) {
            hp2 -= p1.getAttack();
        }

        if (hp2 > 0) {
            hp1 -= p2.getAttack();
        }

        hp1 = Math.max(hp1, 0);
        hp2 = Math.max(hp2, 0);

        p1.setHp(hp1);
        p2.setHp(hp2);

        savePokemon(p1);
        savePokemon(p2);

        Pokemon winner;

        if (hp1 == 0 && hp2 == 0) {
            winner = null;
        } else if (hp1 == 0) {
            winner = p2;
        } else if (hp2 == 0) {
            winner = p1;
        } else {
            int score1 = hp1 + p1.getAttack() + p1.getDefense();
            int score2 = hp2 + p2.getAttack() + p2.getDefense();
            winner = (score1 >= score2) ? p1 : p2;
        }

        return Map.of(
            "pokemon1", p1,
            "pokemon2", p2,
            "winner", winner,
            "result", winner == null ? "draw" : "ok"
        );
    }

    public void resetAllPokemons(List<Pokemon> originalPokemons) throws Exception {
        for (Pokemon p : originalPokemons) {
            savePokemon(p);
        }
    }
}