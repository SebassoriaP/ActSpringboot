// package com.example.demo;

// import com.google.cloud.firestore.Firestore;
// import org.springframework.boot.CommandLineRunner;
// import org.springframework.stereotype.Component;

// import java.util.HashMap;
// import java.util.Map;

// @Component
// public class DataTest implements CommandLineRunner {

//     private final Firestore firestore;

//     public DataTest(Firestore firestore) {
//         this.firestore = firestore;
//     }

//     @Override
//     public void run(String... args) throws Exception {
//         Map<String, Object> data = new HashMap<>();
//         data.put("name", "Pizza Pepperoni");
//         data.put("price", 120);

//         firestore.collection("products")
//                 .document("1")
//                 .set(data)
//                 .get();

//         System.out.println("✅ Data enviada a Firestore");
//     }
// }