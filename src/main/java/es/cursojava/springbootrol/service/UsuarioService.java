package es.cursojava.springbootrol.service;

import java.util.List;

import es.cursojava.springbootrol.model.UsuarioDto;

public interface UsuarioService {

	UsuarioDto registrar(String username, String email, String password, String rol);
	UsuarioDto login(String username, String password);
	List<UsuarioDto> listar();
	UsuarioDto buscarPorId(Long id);
}
