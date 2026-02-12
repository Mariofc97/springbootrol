package es.cursojava.springbootrol.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.ProviderManager;
import org.springframework.security.authentication.dao.DaoAuthenticationProvider;
import org.springframework.security.config.annotation.method.configuration.EnableMethodSecurity;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.security.web.SecurityFilterChain;

@Configuration
@EnableMethodSecurity
public class SecurityConfig {

	private final RoleBasedAuthSuccessHandler successHandler;
	private final CustomAuthFailureHandler customAuthFailureHandler;

	public SecurityConfig(RoleBasedAuthSuccessHandler successHandler,
			CustomAuthFailureHandler customAuthFailureHandler) {
		this.successHandler = successHandler;
		this.customAuthFailureHandler = customAuthFailureHandler;
	}

	@Bean
	public SecurityFilterChain filterChain(HttpSecurity http,
	                                       AuthenticationManager authenticationManager) throws Exception {

	    http.authenticationManager(authenticationManager);

	    http.csrf(csrf -> csrf.disable())
	       .authorizeHttpRequests(auth -> auth
	           .requestMatchers("/", "/login", "/registro", "/css/**").permitAll()
	           .requestMatchers("/admin/**").hasRole("ADMINISTRADOR")
	           .requestMatchers("/api/**").authenticated()
	           .anyRequest().authenticated()
	       )
	       .formLogin(form -> form
	           .loginPage("/")
	           .loginProcessingUrl("/login")
	           .successHandler(successHandler)
	           .failureHandler(customAuthFailureHandler)
	           .permitAll()
	       )
	       
	    // API (Postman)
	       .httpBasic(basic -> {})
	       
	       .logout(logout -> logout
	           .logoutUrl("/logout")
	           .logoutSuccessUrl("/")
	           .invalidateHttpSession(true)
	           .clearAuthentication(true)
	           .permitAll()
	       );

	    return http.build();
	}
	
	@Bean
	public DaoAuthenticationProvider authProvider(UserDetailsService userDetailsService,
	                                             PasswordEncoder passwordEncoder) {
	    DaoAuthenticationProvider provider = new DaoAuthenticationProvider(userDetailsService);
	    provider.setPasswordEncoder(passwordEncoder);
	    return provider;
	}

	@Bean
	public PasswordEncoder passwordEncoder() {
		return new BCryptPasswordEncoder();
	}
	
	@Bean
	public AuthenticationManager authenticationManager(DaoAuthenticationProvider authProvider) {
	    return new ProviderManager(authProvider);
	}
}