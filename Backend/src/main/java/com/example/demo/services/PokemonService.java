package com.example.demo.services;

import java.util.ArrayList;
import java.util.List;

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
            results.add(savePokemon(pokemon));
        }
        return results;
    }
}