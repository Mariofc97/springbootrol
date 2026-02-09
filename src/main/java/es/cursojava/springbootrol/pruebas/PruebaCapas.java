package es.cursojava.springbootrol.pruebas;

import java.util.List;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.criatura.Criatura;
import es.cursojava.springbootrol.entities.equipo.Equipamiento;
import es.cursojava.springbootrol.entities.equipo.objetos.Baya;
import es.cursojava.springbootrol.entities.equipo.objetos.Cuerda;
import es.cursojava.springbootrol.entities.equipo.objetos.HojaParaLimpiar;
import es.cursojava.springbootrol.entities.equipo.objetos.MojonSeco;
import es.cursojava.springbootrol.entities.equipo.objetos.Palo;
import es.cursojava.springbootrol.entities.equipo.objetos.Piedra;
import es.cursojava.springbootrol.exceptions.ReglaJuegoException;
import es.cursojava.springbootrol.model.CriaturaDto;
import es.cursojava.springbootrol.model.EquipamientoDto;
import es.cursojava.springbootrol.model.UsuarioDto;
import es.cursojava.springbootrol.service.CriaturaService;
import es.cursojava.springbootrol.service.EpisodioService;
import es.cursojava.springbootrol.service.EquipamientoService;
import es.cursojava.springbootrol.service.PersonajeService;
import es.cursojava.springbootrol.service.UsuarioService;
import es.cursojava.springbootrol.service.impl.CriaturaServiceImpl;
import es.cursojava.springbootrol.service.impl.EpisodioServiceImpl;
import es.cursojava.springbootrol.service.impl.EquipamientoServiceImpl;
import es.cursojava.springbootrol.service.impl.PersonajeServiceImpl;
import es.cursojava.springbootrol.service.impl.UsuarioServiceImpl;
import es.cursojava.springbootrol.utilidades.JuegoActions;
import utilidades.HibernateUtil;

public class PruebaCapas {

    public static void main(String[] args) {
    	
//    	En Oracle-xe
//    	DROP TABLE TB_EQUIPAMIENTO CASCADE CONSTRAINTS;
//    	DROP TABLE TB_CRIATURA     CASCADE CONSTRAINTS;
//    	DROP TABLE TB_PERSONAJE    CASCADE CONSTRAINTS;
//    	DROP TABLE TB_USUARIO      CASCADE CONSTRAINTS;
    	
    	//PRUEBA DE LA APP:
//    	1. Login y elige personaje
//
//    	2. 12) JUGAR EPISODIO ACTUAL → completa el episodio 1
//
//    	3. Sal del programa
//
//    	4. Vuelve a entrar, login, selecciona el mismo personaje
//
//    	5. 12) JUGAR EPISODIO ACTUAL → debe entrar directamente en episodio 2
//
//    	6. 10) RECARGAR PERSONAJE para comprobar inventario/criaturas guardadas

        HibernateUtil.crearConexion();

        UsuarioService usuarioService = new UsuarioServiceImpl();
        PersonajeService personajeService = new PersonajeServiceImpl();
        EquipamientoService equipamientoService = new EquipamientoServiceImpl();
        CriaturaService criaturaService = new CriaturaServiceImpl();
        EpisodioService episodioService = new EpisodioServiceImpl();

        boolean salir = false;
        UsuarioDto usuarioLogueado = null;
        Personaje personajeCreado = null;

        while (!salir) {
        	mostrarMenu(usuarioLogueado);
        	int op = JuegoActions.pideDatoNumerico("Opcion: ");

            try {
                switch (op) {

                    case 1: {
                        String u = JuegoActions.pideDatoCadena("Username: ");
                        String e = JuegoActions.pideDatoCadena("Email: ");
                        String p = JuegoActions.pideDatoCadena("Password: ");
                        String r = JuegoActions.pideDatoCadena("Rol: ");

                        UsuarioDto registrado = usuarioService.registrar(u, e, p, r);
                        System.out.println("Usuario registrado OK -> " + registrado);
                        break;
                    }

                    case 2: {
                        String ul = JuegoActions.pideDatoCadena("Username: ");
                        String pl = JuegoActions.pideDatoCadena("Password: ");

                        usuarioLogueado = usuarioService.login(ul, pl);
                        System.out.println("ID usuario logueado: " + usuarioLogueado.getId());
                        System.out.println("Usuario logueado OK -> " + usuarioLogueado);

                        List<Personaje> personajes = personajeService.listarPorUsuario(usuarioLogueado.getId());

                        if (personajes.isEmpty()) {
                            personajeCreado = null;
                            System.out.println("No tienes personajes todavía. Crea uno con opción 5.");
                            break;
                        }

                        System.out.println("Elige personaje:");
                        for (int i = 0; i < personajes.size(); i++) {
                            System.out.println((i + 1) + ") " + personajes.get(i).getNombre()
                                    + " [" + personajes.get(i).getRazaTipo() + "]"
                                    + " (id=" + personajes.get(i).getId() + ")");
                        }

                        int idx = JuegoActions.pideDatoNumerico("Opción: ") - 1;
                        if (idx < 0 || idx >= personajes.size()) {
                            personajeCreado = null;
                            System.out.println("Opción inválida.");
                            break;
                        }

                        personajeCreado = personajes.get(idx);
                        System.out.println("Personaje activo: " + personajeCreado);
                        break;
                    }

                    case 3: {
                    	salir = true;
                    	break;
                    }
                    case 4: {
                    	if (usuarioLogueado == null) {
                    		System.out.println("Para crear un personaje debes de hacer login primero");
                    		break;
                    	}
                    	
                    	String name = JuegoActions.pideDatoCadena("Nombre de personaje: ");
                    	String raza = JuegoActions.pideDatoCadena("Elige raza (MONGOL, RAPA NUI, TROGLODITA): ");
                    	
                    	personajeCreado = personajeService.crearYGuardar(usuarioLogueado.getId(), name, raza);
                    	System.out.println("Personaje creado OK -> " + personajeCreado);
                    	break;
                    }

                    case 5: {
                    	if (usuarioLogueado == null) {
                    		System.out.println("Debes de hacer login primero.");
                    		break;
                    	}
                    	
                    	if(personajeCreado == null || personajeCreado.getId() == null) {
                    		System.out.println("Debes seleccionar/crear un personaje antes de jugar.");
                    		break;
                    	}
                    	
                    	try {
                    		personajeCreado = episodioService.jugarEpisodioActual(personajeCreado.getId());
                    		System.out.println("Episodio terminado. Proceso guardado. Episodio actual: " + personajeCreado.getEpisodioActual());
                    	} catch (RuntimeException ex) {
                    		System.out.println("Error general: " + ex.getMessage());
                    	}
                    	break;
                    }


                    case 6: {
                        if (usuarioLogueado == null) {
                            System.out.println("No hay sesión iniciada.");
                            break;
                        }
                        System.out.println("Sesión cerrada del usuario: " + usuarioLogueado.getUsername());
                        usuarioLogueado = null;
                        personajeCreado = null;
                        break;
                    }

                    case 7: {
                    	System.out.println("Usuarios: " + usuarioService.listar());
                    	break;
                    }
                    case 8: {
                        if (usuarioLogueado == null) {
                            System.out.println("Debes hacer login primero.");
                            break;
                        }

                        List<Personaje> lista = personajeService.listarPorUsuario(usuarioLogueado.getId());
                        System.out.println("Personajes del usuario " + usuarioLogueado.getUsername() + ":");
                        for (Personaje pj : lista) {
                            System.out.println(" - " + pj);
                        }
                        break;
                    }

                    case 9: {
                        if (usuarioLogueado == null) {
                            System.out.println("Debes hacer login primero.");
                            break;
                        }
                        if (personajeCreado == null || personajeCreado.getId() == null) {
                            System.out.println("Debes seleccionar/crear un personaje primero (login y elegir personaje, o crear con opción 5).");
                            break;
                        }

                        try {
                            String tipo = JuegoActions.pideDatoCadena(
                                    "¿Qué quieres añadir? (CUERDA, PALO, PIEDRA, MOJON, HOJA, BAYA): ");
                            String t = tipo.trim().toUpperCase();

                            Equipamiento nuevo = null;

                            if ("CUERDA".equals(t)) {
                                nuevo = new Cuerda();
                            } else if ("PALO".equals(t)) {
                                nuevo = new Palo();
                            } else if ("PIEDRA".equals(t)) {
                                nuevo = new Piedra();
                            } else if ("MOJON".equals(t) || "MOJON SECO".equals(t)) {
                                nuevo = new MojonSeco();
                            } else if ("HOJA".equals(t) || "HOJA PARA LIMPIAR".equals(t)) {
                                nuevo = new HojaParaLimpiar();
                            } else if ("BAYA".equals(t)) {
                                nuevo = new Baya();
                            } else {
                                System.out.println("Tipo inválido.");
                                break;
                            }

                            EquipamientoDto añadido = equipamientoService.añadirAlInventario(personajeCreado.getId(), nuevo);
                            System.out.println("OK: añadido -> " + añadido);

                            System.out.println("\nInventario (DTO) del personaje " + personajeCreado.getNombre() + ":");
                            List<EquipamientoDto> inv = equipamientoService.listarPorPersonaje(personajeCreado.getId());
                            for (EquipamientoDto ed : inv) {
                                System.out.println(" - " + ed);
                            }

                        } catch (ReglaJuegoException ex) {
                            System.out.println("Regla del juego: " + ex.getMessage());
                        } catch (RuntimeException ex) {
                            System.out.println("Error técnico: " + ex.getMessage());
                        }

                        break;
                    }

                    case 10: {
                        if (usuarioLogueado == null) {
                            System.out.println("Debes hacer login primero.");
                            break;
                        }

                        Long idPersonaje = null;

                        if (personajeCreado != null && personajeCreado.getId() != null) {
                            System.out.println("Personaje activo detectado: " + personajeCreado.getNombre()
                                    + " (id=" + personajeCreado.getId() + ")");
                            String resp = JuegoActions.pideDatoCadena("¿Quieres recargar ese personaje? (S/N): ")
                                    .trim().toUpperCase();

                            if ("S".equals(resp)) {
                                idPersonaje = personajeCreado.getId();
                            }
                        }

                        if (idPersonaje == null) {
                            idPersonaje = Long.valueOf(JuegoActions.pideDatoNumerico("Introduce el ID del personaje a recargar: "));
                        }

                        try {
                            Personaje recargado = personajeService.buscarPorId(idPersonaje);

                            if (recargado.getUsuario() == null || recargado.getUsuario().getId() == null
                                    || !recargado.getUsuario().getId().equals(usuarioLogueado.getId())) {
                                System.out.println("Ese personaje NO pertenece al usuario logueado.");
                                break;
                            }

                            personajeCreado = recargado;

                            System.out.println("\n--- PERSONAJE CARGADO ---");
                            System.out.println(personajeCreado);

                            System.out.println("\n--- EQUIPO (" + personajeCreado.getEquipo().size() + ") ---");
                            for (Equipamiento eq : personajeCreado.getEquipo()) {
                                System.out.println(" - [" + eq.getClass().getSimpleName() + "] "
                                        + eq.getNombre() + " (id=" + eq.getId() + ")");
                            }

                            System.out.println("\n--- CRIATURAS (" + personajeCreado.getCriaturas().size() + ") ---");
                            for (Criatura c : personajeCreado.getCriaturas()) {
                                System.out.println(" - [" + c.getClass().getSimpleName() + "] "
                                        + c.getNombre() + " alias=" + c.getAlias() + " (id=" + c.getId() + ")");
                            }

                            System.out.println("\nOK: personaje recargado y relaciones verificadas.");

                        } catch (ReglaJuegoException ex) {
                            System.out.println("Regla del juego: " + ex.getMessage());
                        } catch (RuntimeException ex) {
                            System.out.println("Error técnico: " + ex.getMessage());
                        }

                        break;
                    }

                    case 11: {
                        if (usuarioLogueado == null) {
                            System.out.println("Debes hacer login primero.");
                            break;
                        }
                        if (personajeCreado == null) {
                            System.out.println("Debes seleccionar/crear un personaje primero.");
                            break;
                        }

                        try {
                            String tipoC = JuegoActions.pideDatoCadena("Tipo de criatura (GUSANO/CONEJO/MOSQUITO/RATON): ");
                            String aliasC = JuegoActions.pideDatoCadena("Alias (opcional): ");

                            CriaturaDto creada = criaturaService.crearYAsignar(personajeCreado.getId(), tipoC, aliasC);
                            System.out.println("OK: criatura creada -> " + creada);

                            System.out.println("Criaturas actuales del personaje " + personajeCreado.getNombre() + ":");
                            List<CriaturaDto> listaCriaturas = criaturaService.listarPorPersonaje(personajeCreado.getId());
                            for (CriaturaDto cd : listaCriaturas) {
                                System.out.println(" - " + cd);
                            }

                        } catch (ReglaJuegoException ex) {
                            System.out.println("Regla del juego: " + ex.getMessage());
                        } catch (RuntimeException ex) {
                            System.out.println("Error técnico: " + ex.getMessage());
                        }

                        break;
                    }
                    

                    default:
                        System.out.println("Opcion invalida");
                        break;
                }

            } catch (RuntimeException e) {
                System.out.println("Error general " + e.getMessage());
            }
        }

        HibernateUtil.cerrarSessionFactory();
    }
    
    private static boolean esAdmin(UsuarioDto u) {
    	return u != null && "ADMINISTRADOR".equalsIgnoreCase(u.getRol());
    }
    
    private static void mostrarMenu(UsuarioDto usuarioLogueado) {
        System.out.println("\n--- MENU ---");

        // Siempre visibles (sin sesión)
        System.out.println("1) Registrar");
        System.out.println("2) Login");
        System.out.println("3) Salir");

        // Opciones solo si estás logueado (jugador y admin)
        if (usuarioLogueado != null) {
            System.out.println("4) Crear personaje");
            System.out.println("5) JUGAR EPISODIO ACTUAL");
            System.out.println("6) Cerrar sesión");
        }

        // Opciones extra solo ADMIN
        if (esAdmin(usuarioLogueado)) {
            System.out.println("7) Listar usuarios");
            System.out.println("8) Listar personajes por usuario");
            System.out.println("9) TEST: AÑADIR EQUIPAMIENTO POR SERVICE Y LISTAR");
            System.out.println("10) TEST: RECARGAR PERSONAJE Y MOSTRAR INVENTARIO / CRIATURAS (PERSISTENCIA)");
            System.out.println("11) TEST: AÑADIR CRIATURA POR SERVICE Y LISTAR");
        }
    }
    
    
}
