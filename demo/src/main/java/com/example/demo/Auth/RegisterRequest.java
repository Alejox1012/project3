package com.example.demo.Auth;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class RegisterRequest {
    String username;
    String password;
    String confirmPassword; // Nuevo campo para confirmar la contraseña
    String firstname;
    String lastname;
    String country;
}
