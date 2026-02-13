package es.cursojava.springbootrol.controllers;

import java.util.List;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.cursojava.springbootrol.model.UsuarioDto;
import es.cursojava.springbootrol.service.UsuarioService;

@Controller
@PreAuthorize("hasRole('ADMINISTRADOR')")
public class AdminController {

	private final UsuarioService usuarioService;

	public AdminController(UsuarioService usuarioService) {
		this.usuarioService = usuarioService;
	}

	@GetMapping("/admin")
	public String panelAdmin(@RequestParam(value = "soloActivos", required = false) Boolean soloActivos,
			@RequestParam(value = "ok", required = false) String ok,
			@RequestParam(value = "error", required = false) String error, Model model) {

		List<UsuarioDto> usuarios = usuarioService.listar();

		// Si no tienes listarPorActivo en service, filtramos aquí (rápido y simple)
		if (soloActivos != null) {
			usuarios = usuarios.stream().filter(u -> Boolean.TRUE.equals(u.getActivo()) == soloActivos.booleanValue())
					.toList();
		}

		model.addAttribute("usuarios", usuarios);

		if (ok != null)
			model.addAttribute("ok", ok);
		if (error != null)
			model.addAttribute("error", error);

		return "admin_panel";
	}

	@PostMapping("/admin/usuarios")
	public String crearUsuario(@RequestParam String username, @RequestParam String email, @RequestParam String password,
			@RequestParam String rol) {

		try {
			usuarioService.registrar(username, email, password, rol);
			return "redirect:/admin?ok=Usuario creado correctamente";
		} catch (Exception e) {
			return "redirect:/admin?error=" + urlEncode(e.getMessage());
		}
	}

	@PostMapping("/admin/usuarios/{id}/toggle")
	public String toggleActivo(@PathVariable("id") Long id) {
		try {
			usuarioService.toggleActivo(id);
			return "redirect:/admin?ok=Estado del usuario actualizado";
		} catch (Exception e) {
			return "redirect:/admin?error=" + urlEncode(e.getMessage());
		}
	}

	@PostMapping("/admin/usuarios/{id}/rol")
	public String cambiarRol(@PathVariable("id") Long id, @RequestParam String rol) {
		try {
			usuarioService.cambiarRol(id, rol);
			return "redirect:/admin?ok=Rol actualizado";
		} catch (Exception e) {
			return "redirect:/admin?error=" + urlEncode(e.getMessage());
		}
	}

	// Helper simple para evitar que el redirect rompa por espacios
	private String urlEncode(String s) {
		if (s == null)
			return "";
		return s.replace(" ", "%20");
	}
}
