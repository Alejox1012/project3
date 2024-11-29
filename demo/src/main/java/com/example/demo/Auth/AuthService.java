package com.example.demo.Auth;

import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.example.demo.User.Role;
import com.example.demo.User.User;
import com.example.demo.User.UserRepository;
import com.example.demo.jwt.JwtService;

import lombok.RequiredArgsConstructor;

@Service
@RequiredArgsConstructor
public class AuthService {

    private final UserRepository userRepository;
    private final JwtService jwtService;
    private final PasswordEncoder passwordEncoder;
    private final AuthenticationManager authenticationManager;

    public AuthResponse login(LoginRequest request) {
       authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(request.getUsername(), request.getPassword()));
       UserDetails user= userRepository.findByUsername(request.getUsername()).orElseThrow();
       String token=jwtService.getToken(user);
       return AuthResponse.builder()
       .token(token)
       .build();
    }

    public AuthResponse register(RegisterRequest request) {
        User user = User.builder()
             .username(request.getUsername())
             .password(passwordEncoder.encode( request.getPassword()))
             .firstname(request.getFirstname())
             .lastname(request.getLastname())
             .country(request.getCountry())
             .role(Role.USER)
             .build();
        userRepository.save(user);

        return AuthResponse.builder()
          .token(jwtService.getToken(user))
          .build();
    }


     // Método para obtener datos del perfil
     public User getProfile() {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        return userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));
    }

    // Método para actualizar datos del perfil
    public User updateProfile(User updatedUser) {
        String username = SecurityContextHolder.getContext().getAuthentication().getName();
        User user = userRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("User not found"));

        // Solo actualizamos los campos permitidos
        user.setFirstname(updatedUser.getFirstname());
        user.setLastname(updatedUser.getLastname());
        user.setCountry(updatedUser.getCountry());

        return userRepository.save(user);
    }

    

}