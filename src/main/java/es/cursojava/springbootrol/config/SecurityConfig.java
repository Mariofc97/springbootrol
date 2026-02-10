package es.cursojava.springbootrol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@EnableMethodSecurity
@Configuration
public class SecurityConfig {

	// Deshabilitar la seguridad para permitir el acceso sin autenticación
	// Esto es útil para desarrollo o aplicaciones públicas
    @Bean
    public SecurityFilterChain filterChain(HttpSecurity http) throws Exception {

        http
        .csrf(csrf -> csrf.disable())
        .authorizeHttpRequests(auth -> auth
        	    .requestMatchers("/", "/login", "/registro", "/css/**").permitAll()
        	    .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
        	    .anyRequest().authenticated()
        	)
        .formLogin(form -> form
            .loginPage("/")              // tu página index.html
            .loginProcessingUrl("/login")
            .defaultSuccessUrl("/home", true)
            .permitAll()
        )
        .logout(logout -> logout
            .logoutUrl("/logout")        // POST /logout
            .logoutSuccessUrl("/")       // vuelve al login
            .invalidateHttpSession(true)
            .clearAuthentication(true)
            .permitAll()
        );

      return http.build();
    }

    
    @Bean
    public PasswordEncoder passwordEncoder() {
        return new BCryptPasswordEncoder();
    }
}