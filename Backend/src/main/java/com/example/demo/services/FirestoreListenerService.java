package com.example.demo.services;

import java.util.concurrent.CopyOnWriteArrayList;
import java.util.function.Consumer;

import org.springframework.stereotype.Service;

import com.google.cloud.firestore.DocumentChange;
import com.google.cloud.firestore.Firestore;

import jakarta.annotation.PostConstruct;

@Service
public class FirestoreListenerService {

    private final Firestore firestore;

    private final CopyOnWriteArrayList<Consumer<String>> listeners = new CopyOnWriteArrayList<>();

    public FirestoreListenerService(Firestore firestore) {
        this.firestore = firestore;
    }

    @PostConstruct
    public void init() {
        firestore.collection("pokemons")
            .addSnapshotListener((snapshots, e) -> {
                if (e != null) return;

                for (DocumentChange dc : snapshots.getDocumentChanges()) {
                    String change = dc.getType().name();

                    listeners.forEach(listener -> listener.accept(change));
                }
            });
    }

    public void subscribe(Consumer<String> consumer) {
        listeners.add(consumer);
    }

    public void unsubscribe(Consumer<String> consumer) {
        listeners.remove(consumer);
    }
}