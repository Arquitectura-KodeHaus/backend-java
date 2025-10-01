package com.kodehaus.demo.controller;

import com.kodehaus.demo.model.Comerciante;
import com.kodehaus.demo.model.Consumidor;
import com.kodehaus.demo.service.AuthService;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Map;

@RestController
@RequestMapping("/api/auth")
@CrossOrigin(origins = "http://localhost:4200")
public class AuthController {

    private final AuthService authService;

    public AuthController(AuthService authService) {
        this.authService = authService;
    }

    @PostMapping("/register/consumidor")
    public ResponseEntity<?> registerConsumidor(@RequestBody Consumidor consumidor) {
        try {
            String id = authService.registerConsumidor(consumidor);
            return ResponseEntity.ok(Map.of("id", id, "email", consumidor.getEmail(), "tipo", "consumidor"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register/comerciante")
    public ResponseEntity<?> registerComerciante(@RequestBody Comerciante comerciante) {
        try {
            String id = authService.registerComerciante(comerciante);
            return ResponseEntity.ok(Map.of("id", id, "email", comerciante.getEmail(), "tipo", "comerciante"));
        } catch (Exception e) {
            return ResponseEntity.badRequest().body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/login")
public ResponseEntity<?> login(@RequestBody Map<String, String> body) {
    try {
        String token = authService.login(
                body.get("email"),
                body.get("password"),
                body.get("role")
        );
        return ResponseEntity.ok(Map.of("token", token, "role", body.get("role")));
    } catch (Exception e) {
        return ResponseEntity.status(401).body(Map.of("error", e.getMessage()));
    }
}


    @PostMapping("/logout")
    public ResponseEntity<?> logout() {
        return ResponseEntity.ok(Map.of("message", "Logout OK"));
    }
}
