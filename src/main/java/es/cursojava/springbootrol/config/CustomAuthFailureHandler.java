package es.cursojava.springbootrol.config;

import java.io.IOException;
import java.net.URLEncoder;
import java.nio.charset.StandardCharsets;

import org.springframework.security.authentication.DisabledException;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.stereotype.Component;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;

@Component
public class CustomAuthFailureHandler implements AuthenticationFailureHandler {

    @Override
    public void onAuthenticationFailure(HttpServletRequest request,
                                        HttpServletResponse response,
                                        AuthenticationException exception)
            throws IOException, ServletException {

        String msg;

        if (exception instanceof DisabledException) {
            msg = "Usuario desactivado. Contacta con un administrador.";
        } else {
            msg = "Usuario no dado de alta o credenciales incorrectas.";
        }

        String encoded = URLEncoder.encode(msg, StandardCharsets.UTF_8);
        response.sendRedirect("/?errorMsg=" + encoded);
    }
}
