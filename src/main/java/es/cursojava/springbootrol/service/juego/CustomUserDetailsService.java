package es.cursojava.springbootrol.service.juego;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;

import es.cursojava.springbootrol.entities.Usuario;
import es.cursojava.springbootrol.repositories.UsuarioRepository;

@Service
public class CustomUserDetailsService implements UserDetailsService{

	private final UsuarioRepository usuarioRepository;
	@Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Usuario u = usuarioRepository.findByUsername(username)
            .orElseThrow(() -> new UsernameNotFoundException("Usuario no existe"));

        if (!u.isActivo()) {
            throw new DisabledException("Usuario desactivado");
        }

        String role = "ROLE_" + u.getRol().toUpperCase(); // ADMINISTRADOR / JUGADOR

        return org.springframework.security.core.userdetails.User
            .withUsername(u.getUsername())
            .password(u.getPassword()) // debe estar ya cifrada (BCrypt)
            .authorities(role)
            .build();
    }
	public CustomUserDetailsService(UsuarioRepository usuarioRepository) {
		super();
		this.usuarioRepository = usuarioRepository;
	}
	
	

}
