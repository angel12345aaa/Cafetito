package com.login_service.login.controller;

import com.login_service.login.model.Usuario;
import com.login_service.login.security.JwtUtil;
import com.login_service.login.service.UsuarioService;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.HashMap;
import java.util.Map;

@RestController
@RequestMapping("/api/auth")
public class LoginController {

    private final UsuarioService usuarioService;
    private final JwtUtil jwtUtil;

    public LoginController(UsuarioService usuarioService, JwtUtil jwtUtil) {
        this.usuarioService = usuarioService;
        this.jwtUtil = jwtUtil;
    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@RequestBody Map<String, String> credentials) {
        try {
            String nombre = credentials.get("nombre");
            String contrasena = credentials.get("contrasena");

            Usuario usuario = usuarioService.login(nombre, contrasena);
            String token = jwtUtil.generateToken(usuario);

            Map<String, Object> response = new HashMap<>();
            response.put("token", token);
            response.put("rol", usuario.getIdRol());
            response.put("estado", usuario.getEstado());
            response.put("idAgricultor", usuario.getIdAgricultor());

            return ResponseEntity.ok(response);

        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.UNAUTHORIZED)
                    .body(Map.of("error", e.getMessage()));
        }
    }

    @PostMapping("/register")
    public ResponseEntity<?> register(@RequestBody Usuario usuario) {
        try {
            Usuario nuevo = usuarioService.guardar(usuario);
            return ResponseEntity.ok(nuevo);
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.BAD_REQUEST)
                    .body(Map.of("error", e.getMessage()));
        }
    }
}