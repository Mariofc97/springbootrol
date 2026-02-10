package es.cursojava.springbootrol.service.impl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import es.cursojava.springbootrol.entities.Usuario;
import es.cursojava.springbootrol.model.UsuarioDto;
import es.cursojava.springbootrol.repositories.UsuarioRepository;
import es.cursojava.springbootrol.service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {

    private final UsuarioRepository usuarioRepository;
    private final PasswordEncoder passwordEncoder;

    public UsuarioServiceImpl(UsuarioRepository usuarioRepository, PasswordEncoder passwordEncoder) {
        this.usuarioRepository = usuarioRepository;
        this.passwordEncoder = passwordEncoder;
    }

    private static final DateTimeFormatter FMT = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");

    private UsuarioDto mapToDto(Usuario u) {
        if (u == null) return null;
        String fecha = (u.getFechaAlta() == null) ? null : u.getFechaAlta().format(FMT);

        return new UsuarioDto(
                u.getId(),
                u.getUsername(),
                u.getEmail(),
                u.getRol(),
                fecha,
                u.getActivo()
        );
    }

    private Usuario mapToEntity(String username, String email, String password, String rol) {
        return new Usuario(null, username, email, password, rol);
    }

    private String normalizarRol(String rol) {
        if (rol == null) throw new RuntimeException("Rol obligatorio");
        String r = rol.trim().toUpperCase();
        if (!r.equals("JUGADOR") && !r.equals("ADMINISTRADOR")) {
            throw new RuntimeException("Rol inválido. Usa JUGADOR o ADMINISTRADOR");
        }
        return r;
    }

    @Override
    @Transactional
    public UsuarioDto registrar(String username, String email, String password, String rol) {
        if (username == null || username.isBlank()) throw new RuntimeException("Username obligatorio");
        if (email == null || email.isBlank()) throw new RuntimeException("Email obligatorio");
        if (password == null || password.isBlank()) throw new RuntimeException("Password obligatorio");

        if (usuarioRepository.existsByUsername(username)) {
            throw new RuntimeException("El username ya existe: " + username);
        }
        if (usuarioRepository.existsByEmail(email)) {
            throw new RuntimeException("El email ya existe: " + email);
        }

        String rolOk = normalizarRol(rol);

        String hash = passwordEncoder.encode(password);

        Usuario u = mapToEntity(username.trim(), email.trim(), hash, rolOk);
        u.setActivo(Boolean.TRUE);

        usuarioRepository.save(u);
        return mapToDto(u);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto login(String username, String password) {
        if (username == null || username.isBlank() || password == null || password.isBlank()) {
            throw new RuntimeException("Username y password son obligatorios para logearse");
        }

        Usuario u = usuarioRepository.findByUsername(username)
                .orElseThrow(() -> new RuntimeException("No existe el usuario " + username));

        if (u.getActivo() == null || !u.getActivo()) {
            throw new RuntimeException("Usuario desactivado");
        }

        if (!passwordEncoder.matches(password, u.getPassword())) {
            throw new RuntimeException("Password incorrecta");
        }

        return mapToDto(u);
    }

    @Override
    @Transactional(readOnly = true)
    public List<UsuarioDto> listar() {
        List<Usuario> usuarios = usuarioRepository.findAll();
        List<UsuarioDto> dtos = new ArrayList<>();
        for (Usuario u : usuarios) {
            dtos.add(mapToDto(u));
        }
        return dtos;
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto buscarPorId(Long id) {
        if (id == null) throw new RuntimeException("Id de usuario obligatorio");

        Usuario u = usuarioRepository.findById(id)
                .orElseThrow(() -> new RuntimeException("No existe el usuario con id " + id));

        return mapToDto(u);
    }

    @Override
    @Transactional
    public void toggleActivo(Long userId) {
        if (userId == null) throw new RuntimeException("Id de usuario obligatorio");

        Usuario u = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No existe el usuario con id " + userId));

        boolean nuevo = (u.getActivo() == null) ? true : !u.getActivo();
        u.setActivo(nuevo);

        usuarioRepository.save(u);
    }

    @Override
    @Transactional
    public void cambiarRol(Long userId, String rol) {
        if (userId == null) throw new RuntimeException("Id de usuario obligatorio");

        Usuario u = usuarioRepository.findById(userId)
                .orElseThrow(() -> new RuntimeException("No existe el usuario con id " + userId));

        String rolOk = normalizarRol(rol);
        u.setRol(rolOk);

        usuarioRepository.save(u);
    }

    @Override
    @Transactional(readOnly = true)
    public UsuarioDto buscarPorUsername(String username) {
        if (username == null || username.isBlank()) throw new RuntimeException("Username obligatorio");
        Usuario u = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new RuntimeException("No existe el usuario " + username));
        return mapToDto(u);
    }
}
