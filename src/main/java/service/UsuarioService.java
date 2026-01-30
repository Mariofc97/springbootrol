package service;

import java.util.List;

import model.UsuarioDto;

public interface UsuarioService {

	UsuarioDto registrar(String username, String email, String password, String rol);
	UsuarioDto login(String username, String password);
	List<UsuarioDto> listar();
}
