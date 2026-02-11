package es.cursojava.springbootrol.controllers;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestParam;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.exceptions.ReglaJuegoException;
import es.cursojava.springbootrol.model.EquipamientoDto;
import es.cursojava.springbootrol.model.UsuarioDto;
import es.cursojava.springbootrol.service.EquipamientoService;
import es.cursojava.springbootrol.service.PersonajeService;
import es.cursojava.springbootrol.service.UsuarioService;

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
    public String inicio(Model model) {
        // Listar usuarios para facilitar login en demo
        List<UsuarioDto> usuarios = usuarioService.listar();
        model.addAttribute("usuarios", usuarios);
        return "index";
    }
    
    @GetMapping("/registro")
    public String registroForm() {
        return "registro";
    }

    @PostMapping("/registro")
    public String registrar(@RequestParam String username,
                            @RequestParam String email,
                            @RequestParam String password,
                            @RequestParam String rol,
                            Model model) {
        try {
            usuarioService.registrar(username, email, password, rol); // lo creas en el service
            return "redirect:/?registroOk";
        } catch (Exception e) {
            model.addAttribute("error", e.getMessage());
            return "registro";
        }
    }

    
    @GetMapping("/personajes")
    public String personajes(org.springframework.security.core.Authentication auth, Model model) {

        // username del usuario autenticado
        String username = auth.getName();

        // aquí necesitas un método en UsuarioService que busque por username
        UsuarioDto usuario = usuarioService.buscarPorUsername(username);

        List<Personaje> personajes = personajeService.listarPorUsuario(usuario.getId());

        model.addAttribute("usuario", usuario);
        model.addAttribute("usuarioId", usuario.getId());
        model.addAttribute("personajes", personajes);

        return "personajes";
    }
    
    @PostMapping("/personajes/crear")
    public String crear(@RequestParam String nombre,
                        @RequestParam String raza,
                        org.springframework.security.core.Authentication auth) {

        String username = auth.getName();
        UsuarioDto usuario = usuarioService.buscarPorUsername(username);

        personajeService.crearYGuardar(usuario.getId(), nombre, raza);

        return "redirect:/personajes";
    }
    
    @GetMapping("/home")
    public String home(@RequestParam(value="pid", required=false) Long pid, Model model) {
        cargarHome(pid, model);
        return "home";
    }

    @GetMapping("/home/criaturas")
    public String homeCriaturas(@RequestParam("pid") Long pid, Model model) {
        cargarHome(pid, model);
        return "home_criaturas";
    }

    @GetMapping("/home/objetos")
    public String homeObjetos(@RequestParam("pid") Long pid, Model model) {
        cargarHome(pid, model);
        return "home_objetos";
    }

    @GetMapping("/home/armas")
    public String homeArmas(@RequestParam("pid") Long pid, Model model) {
        cargarHome(pid, model);
        return "home_armas";
    }

    @GetMapping("/home/escudos")
    public String homeEscudos(@RequestParam("pid") Long pid, Model model) {
        cargarHome(pid, model);
        return "home_escudos";
    }

    private void cargarHome(Long pid, Model model) {
        if (pid == null) {
            model.addAttribute("personaje", null);
            model.addAttribute("pid", null);
            model.addAttribute("criaturas", List.of());
            model.addAttribute("armas", List.of());
            model.addAttribute("escudos", List.of());
            model.addAttribute("objetos", List.of());
            return;
        }

        try {
            Personaje p = personajeService.cargarParaJuego(pid);
            model.addAttribute("personaje", p);
            model.addAttribute("pid", pid);
            model.addAttribute("criaturas", p.getCriaturas());
            model.addAttribute("armas", equipamientoService.listarArmas(pid));
            model.addAttribute("escudos", equipamientoService.listarEscudos(pid));
            model.addAttribute("objetos", equipamientoService.listarObjetos(pid));
        } catch (ReglaJuegoException e) {
            model.addAttribute("error", e.getMessage());
            model.addAttribute("criaturas", List.of());
            model.addAttribute("armas", List.of());
            model.addAttribute("escudos", List.of());
            model.addAttribute("objetos", List.of());
        }
    }

    
//    @GetMapping("/personaje/{id}")
//    public String verPersonaje(@PathVariable Long id, Model model) {
//        try {
//            Personaje p = personajeService.cargarParaJuego(id); // trae todo
//            model.addAttribute("personaje", p);
//            return "inventario"; // Reusing inventario view for character details
//        } catch (ReglaJuegoException e) {
//            return "redirect:/"; // error handling simplified
//        }
//    }
//
//    @PostMapping("/personaje/{id}/fabricar")
//    public String fabricar(@PathVariable Long id, @RequestParam String tipo) {
//        try {
//            equipamientoService.fabricar(id, tipo);
//        } catch (ReglaJuegoException e) {
//            // Add flash message ideally
//        }
//        return "redirect:/personaje/" + id;
//    }
//
//    @PostMapping("/personaje/{id}/eliminar-item")
//    public String eliminarItem(@PathVariable Long id, @RequestParam Long equipId) {
//        try {
//            equipamientoService.eliminarDeInventario(id, equipId);
//        } catch (ReglaJuegoException e) {
//            // error
//        }
//        return "redirect:/personaje/" + id;
//    }
//    
//    @PostMapping("/personaje/{id}/consumir")
//    public String consumirItem(@PathVariable Long id, @RequestParam Long equipId) {
//        try {
//            equipamientoService.consumirCurativo(id, equipId);
//        } catch (ReglaJuegoException e) {
//            // error
//        }
//        return "redirect:/personaje/" + id;
//    }

}
