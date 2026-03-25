package com.example.demo;

import com.google.cloud.firestore.Firestore;
import org.springframework.stereotype.Component;

@Component
public class TestFirebase {

    public TestFirebase(Firestore firestore) {
        System.out.println("🔥 Firebase conectado correctamente");
    }
}