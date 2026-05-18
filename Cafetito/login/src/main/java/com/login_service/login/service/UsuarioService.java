package com.login_service.login.service;

import com.login_service.login.model.Usuario;
import com.login_service.login.repository.UsuarioRepository;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioService(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    public Usuario login(String nombre, String contrasena) {

        Usuario usuario = usuarioRepository.findByNombre(nombre)
                .orElseThrow(() -> new RuntimeException("Usuario no encontrado"));

        if (!passwordEncoder.matches(contrasena, usuario.getContrasena())) {
            throw new RuntimeException("Contraseña incorrecta");
        }

        return usuario;
    }

    public Usuario guardar(Usuario usuario) {
        usuario.setContrasena(passwordEncoder.encode(usuario.getContrasena()));
        return usuarioRepository.save(usuario);
    }

}