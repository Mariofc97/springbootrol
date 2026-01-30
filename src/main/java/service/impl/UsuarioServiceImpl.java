package service.impl;

import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.UsuarioDto;
import entities.Usuario;
import repositories.UsuarioRepository;
import service.UsuarioService;

@Service
public class UsuarioServiceImpl implements UsuarioService {
	
	private final UsuarioRepository usuarioRepository;
	
	public UsuarioServiceImpl(UsuarioRepository usuarioRepository) {
	    this.usuarioRepository = usuarioRepository;
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
	
	// En este caso seria para crear usuarios pasando todos los parametros
	private Usuario mapToEntity(String username, String email, String password, String rol) {
		return new Usuario(null, username, email, password, rol);
	}

	@Override
	@Transactional
	public UsuarioDto registrar(String username, String email, String password, String rol) {
		if(username == null || username.isBlank()) throw new RuntimeException("Username obligatorio");
		if(email == null || email.isBlank()) throw new RuntimeException("Email obligatorio"); // Fix typo in original: checked username instead of email
		if(password == null || password.isBlank()) throw new RuntimeException("Password obligatorio"); // Fix typo: checked username
		if(rol == null || rol.isBlank()) throw new RuntimeException("Rol obligatorio"); // Fix typo: checked username
		
		if(usuarioRepository.existsByUsername(username)) {
			throw new RuntimeException("El username ya existe: " + username);
		}
		if(usuarioRepository.existsByEmail(email)) {
			throw new RuntimeException("El email ya existe: " + email);
		}
		
		rol = rol.trim().toUpperCase();

		if (!rol.equals("JUGADOR") && !rol.equals("ADMINISTRADOR")) {
		    throw new RuntimeException("Rol inválido. Usa JUGADOR o ADMINISTRADOR");
		}
		
		Usuario u = mapToEntity(username, email, password, rol);
		usuarioRepository.save(u);
		
		return mapToDto(u);
	}

	@Override
	public UsuarioDto login(String username, String password) {
		if(username == null || username.isBlank() || password == null || password.isBlank()) {
			throw new RuntimeException("Username y password son obligatorios para logearse");
		}
		
		Usuario u = usuarioRepository.findByUsername(username)
		        .orElseThrow(() -> new RuntimeException("No existe el usuario " + username));
		
		if (!u.getPassword().equals(password)) {
			throw new RuntimeException("Password incorrecta");
		}
		
		return mapToDto(u);
	}

	@Override
	public List<UsuarioDto> listar() {
		List<Usuario> usuarios = usuarioRepository.findAll();
		List<UsuarioDto> dtos = new ArrayList<>();
		
		for (Usuario u : usuarios) {
			dtos.add(mapToDto(u));
		}
		
		return dtos;
	}

}
