package es.cursojava.springbootrol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.core.userdetails.UsernameNotFoundException;

import es.cursojava.springbootrol.entities.Usuario;
import es.cursojava.springbootrol.repositories.UsuarioRepository;

@Configuration
public class UserDetailsConfig {

    @Bean
    public UserDetailsService userDetailsService(UsuarioRepository usuarioRepository) {
        return username -> {
            Usuario u = usuarioRepository.findByUsername(username)
                    .orElseThrow(() -> new UsernameNotFoundException("No existe el usuario " + username));

            boolean enabled = Boolean.TRUE.equals(u.getActivo()); // ✅ aquí

            return User.withUsername(u.getUsername())
                    .password(u.getPassword())
                    .roles(u.getRol()) // "ADMINISTRADOR" o "JUGADOR" -> genera ROLE_...
                    .disabled(!enabled) // ✅ si está desactivado, Spring lanza DisabledException
                    .build();
        };
    }
}
