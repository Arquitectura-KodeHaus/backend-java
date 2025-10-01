package com.kodehaus.demo.service;

import com.google.api.core.ApiFuture;
import com.google.cloud.firestore.*;
import com.google.firebase.cloud.FirestoreClient;
import com.kodehaus.demo.model.Comerciante;
import com.kodehaus.demo.model.Consumidor;
import com.kodehaus.demo.utils.JwtUtil;
import org.springframework.stereotype.Service;

import java.util.concurrent.ExecutionException;

@Service
public class AuthService {

    private final Firestore db;
    private final JwtUtil jwtUtil;

public AuthService(JwtUtil jwtUtil, Firestore firestore) {
    this.jwtUtil = jwtUtil;
    this.db = firestore;
}



    
    // REGISTRO CONSUMIDOR
    public String registerConsumidor(Consumidor consumidor) throws Exception {
        ApiFuture<QuerySnapshot> query = db.collection("consumidores")
                .whereEqualTo("email", consumidor.getEmail()).get();
        if (!query.get().isEmpty()) {
            throw new Exception("El consumidor ya existe");
        }
        DocumentReference ref = db.collection("consumidores").document();
        consumidor.setId(ref.getId());
        ref.set(consumidor).get();
        return consumidor.getId();
    }

    // REGISTRO COMERCIANTE
    public String registerComerciante(Comerciante comerciante) throws Exception {
        ApiFuture<QuerySnapshot> query = db.collection("comerciantes")
                .whereEqualTo("email", comerciante.getEmail()).get();
        if (!query.get().isEmpty()) {
            throw new Exception("El comerciante ya existe");
        }
        DocumentReference ref = db.collection("comerciantes").document();
        comerciante.setId(ref.getId());
        ref.set(comerciante).get();
        return comerciante.getId();
    }

   
    public String login(String email, String password, String role) throws Exception {
    // Selecciona la colección según el rol
    String collection = role.equals("consumidor") ? "consumidores" : "comerciantes";

    QuerySnapshot query = db.collection(collection)
            .whereEqualTo("email", email).get().get();

    if (!query.isEmpty()) {
        if (role.equals("consumidor")) {
            Consumidor c = query.getDocuments().get(0).toObject(Consumidor.class);
            if (c.getPassword().equals(password)) {
                return jwtUtil.generateToken(c.getId(), c.getEmail());
            }
        } else {
            Comerciante v = query.getDocuments().get(0).toObject(Comerciante.class);
            if (v.getPassword().equals(password)) {
                return jwtUtil.generateToken(v.getId(), v.getEmail());
            }
        }
        throw new Exception("Contraseña incorrecta");
    }
    throw new Exception("Usuario no encontrado");
}

}
