package com.example.demo.Auth;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.example.demo.User.User;

import lombok.RequiredArgsConstructor;

@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    @PostMapping("login")
    public ResponseEntity<AuthResponse> login(@RequestBody LoginRequest request) {
        return ResponseEntity.ok(authService.login(request));
    }

    @PostMapping("register")
    public ResponseEntity<AuthResponse> register(@RequestBody RegisterRequest request) {
        return ResponseEntity.ok(authService.register(request));
    }

    @GetMapping("profile")
    public ResponseEntity<User> getProfile() {
        // Obtener datos del perfil del usuario autenticado
        return ResponseEntity.ok(authService.getProfile());
    }

    @PutMapping("profile")
    public ResponseEntity<User> updateProfile(@RequestBody User updatedUser) {
        // Actualizar información del perfil del usuario
        return ResponseEntity.ok(authService.updateProfile(updatedUser));
    }
}
