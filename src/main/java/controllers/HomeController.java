package controllers;

import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import model.UsuarioDto;
import entities.Personaje;
import exceptions.ReglaJuegoException;
import service.EquipamientoService;
import service.PersonajeService;
import service.UsuarioService;

@Controller
public class HomeController {

    private final UsuarioService usuarioService;
    private final PersonajeService personajeService;
    private final EquipamientoService equipamientoService;

    public HomeController(UsuarioService usuarioService, PersonajeService personajeService, EquipamientoService equipamientoService) {
        this.usuarioService = usuarioService;
        this.personajeService = personajeService;
        this.equipamientoService = equipamientoService;
    }

    @GetMapping("/")
    public String home(Model model) {
        // Listar usuarios para facilitar login en demo
        List<UsuarioDto> usuarios = usuarioService.listar();
        model.addAttribute("usuarios", usuarios);
        return "index";
    }

    @PostMapping("/login")
    public String login(@RequestParam String username, @RequestParam String password, Model model) {
        try {
            UsuarioDto usuario = usuarioService.login(username, password);
            List<Personaje> personajes = personajeService.listarPorUsuario(usuario.getId());
            model.addAttribute("usuario", usuario);
            model.addAttribute("personajes", personajes);
            return "personajes"; 
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "index";
        }
    }
    
    // Simplification for demo: show characters of a specific user directly if needed
    // or just handle selection Flow
    
    @GetMapping("/personaje/{id}")
    public String verPersonaje(@PathVariable Long id, Model model) {
        try {
            Personaje p = personajeService.cargarParaJuego(id); // trae todo
            model.addAttribute("personaje", p);
            return "inventario"; // Reusing inventario view for character details
        } catch (ReglaJuegoException e) {
            return "redirect:/"; // error handling simplified
        }
    }

    @PostMapping("/personaje/{id}/fabricar")
    public String fabricar(@PathVariable Long id, @RequestParam String tipo) {
        try {
            equipamientoService.fabricar(id, tipo);
        } catch (ReglaJuegoException e) {
            // Add flash message ideally
        }
        return "redirect:/personaje/" + id;
    }

    @PostMapping("/personaje/{id}/eliminar-item")
    public String eliminarItem(@PathVariable Long id, @RequestParam Long equipId) {
        try {
            equipamientoService.eliminarDeInventario(id, equipId);
        } catch (ReglaJuegoException e) {
            // error
        }
        return "redirect:/personaje/" + id;
    }
    
    @PostMapping("/personaje/{id}/consumir")
    public String consumirItem(@PathVariable Long id, @RequestParam Long equipId) {
        try {
            equipamientoService.consumirCurativo(id, equipId);
        } catch (ReglaJuegoException e) {
            // error
        }
        return "redirect:/personaje/" + id;
    }

}
