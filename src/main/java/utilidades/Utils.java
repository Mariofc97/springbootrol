package utilidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.concurrent.ThreadLocalRandom;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import dao.PersonajeDao;
import dao.impl.PersonajeDaoImpl;
import entities.Personaje;
import entities.criatura.Conejo;
import entities.criatura.Criatura;
import entities.criatura.Gusano;
import entities.criatura.Jabali;
import entities.criatura.Lobo;
import entities.criatura.Mosquito;
import entities.criatura.Raton;
import entities.equipo.Equipamiento;
import entities.equipo.armas.Arco;
import entities.equipo.armas.Armas;
import entities.equipo.armas.Bumeran;
import entities.equipo.armas.CanaPescar;
import entities.equipo.armas.Cazamariposas;
import entities.equipo.armas.Honda;
import entities.equipo.armas.Lanza;
import entities.equipo.armas.Trampa;
import entities.equipo.escudos.EscudoMadera;
import entities.equipo.escudos.EscudoPiedra;
import entities.equipo.escudos.Escudos;
import entities.equipo.objetos.Baya;
import entities.equipo.objetos.CarneSeca;
import entities.equipo.objetos.Cuerda;
import entities.equipo.objetos.HojaParaLimpiar;
import entities.equipo.objetos.MojonSeco;
import entities.equipo.objetos.Palo;
import entities.equipo.objetos.Piedra;
import entities.equipo.objetos.Pocion;
import exceptions.ReglaJuegoException;
import model.CriaturaDto;
import model.EquipamientoDto;
import service.CriaturaService;
import service.EquipamientoService;
import service.PersonajeService;
import service.impl.CriaturaServiceImpl;
import service.impl.EquipamientoServiceImpl;
import service.impl.PersonajeServiceImpl;

public class Utils {

	private static final PersonajeService personajeService = new PersonajeServiceImpl();
	private static final CriaturaService criaturaService = new CriaturaServiceImpl();
	protected static final Logger log = LoggerFactory.getLogger(Utils.class);
	private static boolean ultimaCazaExitosa = false;
	// TODO
	// Metodos

	// ganarEquipo();

	/**
	 * @param ganaEquipo equipo que queremos añadir al personaje clase Equipamiento
	 * @param person     personaje al que añadimos equipo, clase Personaje
	 */
	public static void ganarEquipo(Equipamiento ganaEquipo, Personaje person) {

		person.addEquipamiento(ganaEquipo);
		System.out.println("Has ganado equipo: " + ganaEquipo.toString());

	}

	// invocacion();

	public static Criatura invocacionCompañeroCriatura(Personaje person) {

	    if (person == null || person.getId() == null) {
	        System.out.println("Error: personaje no válido o no persistido.");
	        return null;
	    }

	    // narrativa: “qué sale”
	    Criatura compiRandom = randomizarCriatura();

	    // tirada (90% éxito)
	    boolean ok = dadoDiez() > 1;
	    if (!ok) {
	        System.out.println("No estás pensando en lo que debes, la criatura se ríe de ti y te ataca.");
	        person.setPuntosVida(person.getPuntosVida() - compiRandom.getPuntosAtaque());
	        System.out.println("Te ha quitado " + compiRandom.getPuntosAtaque() +
	                " puntos de vida, te quedan " + person.getPuntosVida() + " puntos de vida.");
	        return null;
	    }

	    System.out.println("Ahora tienes un compañero de viaje, ¿quieres ponerle un alias?:");
	    String alias = pideDatoCadena("Introduce el alias deseado: ");

	    // tipo para el service (MOSQUITO/CONEJO/...)
	    String tipo = compiRandom.getClass().getSimpleName().toUpperCase();

	    try {
	        CriaturaDto dto = criaturaService.invocarCompanero(person.getId(), tipo, alias);

	        System.out.println("Has invocado una criatura: " + dto.getTipo() + " alias=" + dto.getAlias());

	        // mantener coherencia en memoria también:
	        compiRandom.setId(dto.getId());
	        compiRandom.setNombre(dto.getNombre());
	        compiRandom.setAlias(dto.getAlias());
	        compiRandom.setNivel(dto.getNivel());
	        compiRandom.setExperiencia(dto.getExperiencia());
	        compiRandom.setPuntosVida(dto.getPuntosVida());
	        compiRandom.setPuntosAtaque(dto.getPuntosAtaque());
	        person.addCriatura(compiRandom);

	        return compiRandom;

	    } catch (ReglaJuegoException e) {
	        System.out.println("No se pudo invocar: " + e.getMessage());
	        return null;
	    }
	}

	public static int contarHojas(Personaje personaje) {
		int contador = 0;

		for (Object obj : personaje.getEquipo()) {
			if (obj instanceof HojaParaLimpiar) {
				contador++;
			}
		}

		return contador;
	}

	// este random solo esta hecho con 4 criaturas, habrá que meter mas si se
	// generan mas
	public static Criatura randomizarCriatura() {

		int tirada = ThreadLocalRandom.current().nextInt(1, 5);

		Criatura c;
		switch (tirada) {
		case 1:
			System.out.println("Tirada de criatura: Gusano.");
			c = new Gusano();
			break;
		case 2:
			System.out.println("Tirada de criatura: Conejo.");
			c = new Conejo();
			break;
		case 3:
			System.out.println("Tirada de criatura: Mosquito.");
			c = new Mosquito();
			break;
		default:
			System.out.println("Tirada de criatura: Raton.");
			c = new Raton();
			break;
		}
		return c;
	}

	/**
	 * @return devuelve int resultado tirada
	 */
	public static int dadoDiez() {
		int tirada = dadoNumeroDefine(10);
		return tirada;
	}

	public static int dadoNumeroDefine(int numero) {
		int tirada = (int) (Math.random() * numero + 1);

		StackTraceElement[] st = Thread.currentThread().getStackTrace();
		String callerMethod = "desconocido";

		// 0=getStackTrace, 1=dadoNumeroDefine, 2=dadoDiez (si viene de ahí), 3=llamador
		// real
		if (st.length > 3) {
			callerMethod = st[3].getMethodName(); // SOLO el nombre del método
		}

		System.out.println("[TIRADA DE DADO] d" + numero + " -> " + tirada + " (en " + callerMethod + ")");
		return tirada;
	}

	// metodo nuevo.
	public static String desgraciaAleatorio() {
		String[] nombres = { "Hay tormenta y te cae un rayo, hueles a pelo quemado.",
				"Hay ventisca tropiezas y te caes por el acantilado de al lado de la cueva, te partes dos costillas.",
				"Esta helando y no tienes ropa, coges una hipotermia.",
				"Hay una ola de calor y te deshidratas, te rescatan los niños.",
				"Habia un dientes de sable acechando y sales con vida gracias a que hueles muy mal y no ha querido comerte.",
				"El día es explendido te distraes disfrutando de la tarde y un mamut te arrolla.",
				"Los extraterrestre te secuestran y experimenta contigo, estas para el arrastre.",
				"Esta lloviendo mucho y decides volver, pero al volver te pilla una riada, casi te ahogas." };
		String nombre = nombres[ThreadLocalRandom.current().nextInt(nombres.length)];
		return nombre;
	}

	public static int pideDatoNumerico(String texto) {
		Scanner scan = new Scanner(System.in);
		int numero;

		while (true) {
			System.out.println(texto);

			if (scan.hasNextInt()) { // Comprueba si es un número entero
				numero = scan.nextInt();
				return numero; // Devuelve el número válido
			} else {
				System.out.println("No has introducido un valor correcto. Inténtalo de nuevo.");
				scan.nextLine(); // Limpia el buffer
			}
		}
	}
	
	private static void syncPersonaje(Personaje person, Personaje source) {
	    person.setExperiencia(source.getExperiencia());
	    person.setNivel(source.getNivel());
	    person.setPuntosVidaMax(source.getPuntosVidaMax());
	    person.setPuntosAtaque(source.getPuntosAtaque());
	    person.setPuntosVida(source.getPuntosVida());   
	    person.setEquipo(source.getEquipo());
	    person.setCriaturas(source.getCriaturas());
	}

	public static boolean combate(Personaje person, Criatura enemigo) {

		boolean ganador = false;
		pausa(500);
		System.out.println("\n==============================");
		System.out.println("        EMPIEZA EL COMBATE!		");
		System.out.println("\n " + person.getNombre() + " VS " + enemigo.getNombre());
		System.out.println("==============================\n");

		if (!person.tieneArmaEquipada()) {
			System.out.println(
					"El personaje " + person.getNombre() + " no tiene arma equipada. No puedes combatir sin arma.");
			System.out.println(enemigo.getNombre() + " te revienta y te deja a 1 punto de vida.");
			person.setPuntosVida(1);
			System.out.println("Escapas como puedes. PV: " + person.getPuntosVida());
			return false;
		}

		if (person.getCriaturas() == null || person.getCriaturas().isEmpty()) {
			System.out.println("No puedes combatir sin un compañero criatura. Primero invoca uno.");
			return false;
		}

		int turno = 1;

		while (person.estaVivo() && enemigo.estaVivo() && person.tieneArmaEquipada()) {

			System.out.println("\n--- TURNO " + turno + " ---");
			mostrarEstadoCombate(person, enemigo);

			System.out.println("\nQue haces?");
			System.out.println("1) Atacar");
			System.out.println("2) Consumir objeto (Baya / CarneSeca / Pocion)");
			System.out.println("3) Huir");

			int opcion = pideDatoNumerico("Elige: ");

			if (opcion == 3) {
				System.out.println("Huyes del combate como buen cobarde que eres...");
				return false;
			}

			if (opcion == 2) {
				boolean consumido = consumirCurativoConService(person);
				if (!consumido) {
					System.out.println("No consumes nada.");
				}
				pausa(300);
			} else {
				int danioHecho = person.atacar(enemigo);
				System.out.println(person.getNombre() + " hace " + danioHecho + " de daño a " + enemigo.getNombre());
				System.out.println("Vida del enemigo: " + enemigo.getPuntosVida());

				pausa(300);

				if (!enemigo.estaVivo()) {
				    try {
						Personaje actualizado = personajeService.sumarExperiencia(person.getId(), 10);
						syncPersonaje(person, actualizado);
				    } catch (ReglaJuegoException e) {
				        System.out.println("No se pudo aplicar experiencia: " + e.getMessage());
				        log.warn("Error sumarExperiencia", e);
				    } 
				    System.out.println(enemigo.getNombre() + " ha sido derrotado.");
				    ganador = true;
				    break;
				}

				// Turno del compañero
				Criatura companero = obtenerCompaneroActivo(person);
				if (companero != null && enemigo.estaVivo()) {
					int danioComp = companero.atacar(enemigo);
					System.out.println(companero.getAlias() + " (" + companero.getNombre() + ") hace " + danioComp
							+ " de dano a " + enemigo.getNombre());
					System.out.println("Vida del enemigo: " + enemigo.getPuntosVida());

					if (!enemigo.estaVivo()) {
					    try {
							Personaje actualizado = personajeService.sumarExperiencia(person.getId(), 10);
							syncPersonaje(person, actualizado);
					    } catch (ReglaJuegoException e) {
					        System.out.println("No se pudo aplicar experiencia: " + e.getMessage());
					        log.warn("Error sumarExperiencia", e);
					    } 
					    System.out.println(enemigo.getNombre() + " ha sido derrotado.");
					    ganador = true;
					    break;
					}
				}
			}

			System.out.println("\nTurno de " + enemigo.getNombre() + "...");
			pausa(300);

			int danioRecibido = enemigo.atacar(person);
			System.out.println(enemigo.getNombre() + " hace " + danioRecibido + " de daño. Vida de "
					+ person.getNombre() + ": " + person.getPuntosVida());

			pausa(300);

			if (!person.estaVivo()) {
				System.out.println("...Has perdido...");
				System.out.println(person.getNombre() + " ha caido en combate.");
				ganador = false;
				break;
			}

			turno++;
		}

		System.out.println("\n==============================");
		System.out.println("        FIN DEL COMBATE");
		System.out.println("==============================\n");
		return ganador;
	}

	private static Criatura obtenerCompaneroActivo(Personaje person) {
		if (person.getCriaturas() == null || person.getCriaturas().isEmpty())
			return null;

		// Si tu Criatura no tiene "estaVivo()", puedes quitar esta comprobación
		for (Criatura c : person.getCriaturas()) {
			if (c != null && c.estaVivo()) {
				return c;
			}
		}
		return null;
	}

	private static boolean consumirCurativoConService(Personaje person) {
	    try {
	        EquipamientoService es = new EquipamientoServiceImpl();

	        List<EquipamientoDto> curativos = es.listarConsumiblesCurativos(person.getId());
	        if (curativos.isEmpty()) {
	            System.out.println("No tienes consumibles curativos (Baya, CarneSeca o Pocion).");
	            return false;
	        }

	        System.out.println("\n--- CONSUMIBLES CURATIVOS ---");
	        for (int i = 0; i < curativos.size(); i++) {
	            EquipamientoDto d = curativos.get(i);
	            System.out.println((i + 1) + ") " + d.getNombre() + " [id=" + d.getId() + "]"
	                    + " durabilidad=" + d.getDurabilidad());
	        }
	        System.out.println((curativos.size() + 1) + ") Cancelar");

	        int opcion = pideDatoNumerico("Elige: ");
	        if (opcion < 1 || opcion > curativos.size()) return false;

	        EquipamientoDto elegido = curativos.get(opcion - 1);

	        int antes = person.getPuntosVida();
	        int despues = es.consumirCurativo(person.getId(), elegido.getId());

	        // IMPORTANTE: actualizar el objeto Personaje en memoria para que el combate muestre PV correcto
	        person.setPuntosVida(despues);

	        System.out.println("Has consumido " + elegido.getNombre() + ". PV: " + antes + " -> " + despues
	                + " / " + person.getPuntosVidaMax());

	        return true;

	    } catch (ReglaJuegoException e) {
	        System.out.println("No puedes consumir: " + e.getMessage());
	        return false;
	    }
	}

	private static void pausa(long ms) {
		try {
			Thread.sleep(ms);
		} catch (InterruptedException ex) {
			Thread.currentThread().interrupt();
		}
	}

	private static void mostrarEstadoCombate(Personaje person, Criatura enemigo) {
		System.out.println(person.getNombre() + " PV: " + person.getPuntosVida() + "/" + person.getPuntosVidaMax());

		Criatura companero = obtenerCompaneroActivo(person);
		if (companero != null) {
			System.out.println(companero.getAlias() + " (" + companero.getNombre() + ") PV: "
					+ companero.getPuntosVida() + "/" + companero.getPuntosVida());
			// Si tu Criatura tiene "puntosVidaMax", entonces usa: companero.getPuntosVida()
			// + "/" + companero.getPuntosVidaMax()
		} else {
			System.out.println();
		}

		System.out.println(enemigo.getNombre() + " PV: " + enemigo.getPuntosVida());
	}

	public static void invocarLoboJabali(Personaje person) {

	    if (person == null || person.getId() == null) {
	        System.out.println("Error: personaje no válido o no persistido.");
	        return;
	    }

	    Lobo lobo = new Lobo();
	    Jabali jabali = new Jabali();

	    int tirada = dadoDiez();

	    if (tirada == 1) {
	        System.out.println(
	            "Mientras invocas al lobo un mosquito te pica y te distraes, el lobo se enfada y te ataca."
	        );
	        combate(person, lobo);
	        return;
	    }

	    if (tirada == 9) {
	        System.out.println(
	            "Mientras invocas al jabalí un ratón te asusta y te distraes, el jabalí se enfada y te ataca."
	        );
	        combate(person, jabali);
	        return;
	    }

	    // Éxito: elegimos qué criatura se invoca según tirada
	    String tipo;
	    String nombreDefault;

	    if (tirada > 1 && tirada < 5) {
	        System.out.println("Has invocado correctamente a un lobo.");
	        tipo = "LOBO";
	        nombreDefault = "Lobo";
	    } else { // tirada >= 5 && tirada < 9
	        System.out.println("Has invocado correctamente a un jabalí.");
	        tipo = "JABALI";
	        nombreDefault = "Jabali";
	    }

	    // Pedimos alias
	    String alias = pideDatoCadena("¿Quieres ponerle un alias? (Enter para dejar el nombre): ");
	    if (alias == null || alias.trim().isEmpty()) {
	        alias = nombreDefault;
	    }

	    try {
	        // Persistimos criatura (FK personaje + save)
	        CriaturaDto dto = criaturaService.invocarCompanero(person.getId(), tipo, alias);

	        System.out.println("Criatura guardada en BD: " + dto.getTipo() + " alias=" + dto.getAlias());

	        // Muy importante: sincronizar el objeto en memoria para que el episodio lo vea al instante
	        Personaje rec = recargarPersonaje(person.getId());
	        if (rec != null) {
	            person.setCriaturas(rec.getCriaturas());
	            person.setEquipo(rec.getEquipo());
	            person.setPuntosVida(rec.getPuntosVida());
	            person.setExperiencia(rec.getExperiencia());
	            person.setNivel(rec.getNivel());
	        }

	    } catch (ReglaJuegoException e) {
	        System.out.println("No se pudo invocar: " + e.getMessage());
	    }
	}

	private static void mostrarEquipoCompleto(Personaje person) {
		List<Equipamiento> equipo = person.getEquipo();

		if (equipo == null || equipo.isEmpty()) {
			System.out.println("No llevas ningun objeto encima");
			return;
		}

		System.out.println("\n--- EQUIPO COMPLETO ---");
		for (int i = 0; i < equipo.size(); i++) {
			Equipamiento e = equipo.get(i);
			String tipoEq = obtenerTipoEquipamiento(e);
			System.out.println((i + 1) + ". [" + tipoEq + "]" + e.getNombre() + " (peso: " + e.getPeso()
					+ ", durabilidad: " + e.getDurabilidad() + ")");
			// añadir diferenciacion entre armas, pociones, escudos, comida, etc

		}
	}

	public static void recuperarVida(Personaje personaje) {

		personaje.setPuntosVida(personaje.getPuntosVidaMax());

	}

	private static void mostrarCompaneros(Personaje person) {
	    if (person == null || person.getId() == null) {
	        System.out.println("No hay personaje válido.");
	        return;
	    }

	    try {
	        List<CriaturaDto> lista = criaturaService.listarPorPersonaje(person.getId());

	        if (lista.isEmpty()) {
	            System.out.println("No tienes criaturas aliadas.");
	            return;
	        }

	        for (CriaturaDto c : lista) {
	            System.out.println("Criatura: " + c.getNombre()
	                    + " | Tipo: " + c.getTipo()
	                    + " | Alias: " + c.getAlias()
	                    + " | PV: " + c.getPuntosVida()
	                    + " | ATQ: " + c.getPuntosAtaque());
	        }

	    } catch (ReglaJuegoException e) {
	        System.out.println("Error listando criaturas: " + e.getMessage());
	    }
	}


	private static String obtenerTipoEquipamiento(Equipamiento e) {
		if (e instanceof Armas) {
			return "Arma";
		} else if (e instanceof Escudos) {
			return "Escudo";
		} else if (e instanceof Pocion) {
			return "Pocion";
		} else {
			return "Objeto";
		}
	}

	// Este menu es para ver armas pero tambien para EQUIPAR!
	// Equipar: mover el arma elegida al principio de la lista para que
	// getArmaEquipada() pueda encontrarla primero.
	private static void menuArmas(Personaje person) {
	    List<Equipamiento> equipo = person.getEquipo();
	    if (equipo == null || equipo.isEmpty()) {
	        System.out.println("No llevas armas ni objetos");
	        return;
	    }

	    List<Armas> armas = new ArrayList<>();
	    for (Equipamiento e : equipo) {
	        if (e instanceof Armas) armas.add((Armas) e);
	    }

	    if (armas.isEmpty()) {
	        System.out.println("No tienes ninguna arma en el inventario");
	        return;
	    }

	    System.out.println("\n--- ARMAS ---");
	    for (int i = 0; i < armas.size(); i++) {
	        Armas a = armas.get(i);
	        System.out.println((i + 1) + ") " + a.getNombre() +
	            " [id=" + a.getId() + "]" +
	            " daño=" + a.getPuntosDaño() +
	            " durabilidad=" + a.getDurabilidad() +
	            " nivelReq=" + a.getNivelRequerido());
	    }

	    System.out.println((armas.size() + 1) + ") Volver");
	    int opcion = pideDatoNumerico("Elige un arma para equipar:");

	    if (opcion < 1 || opcion > armas.size()) {
	        System.out.println("Volviendo sin cambiar arma.");
	        return;
	    }

	    Armas seleccionada = armas.get(opcion - 1);

	    try {
	        EquipamientoService es = new EquipamientoServiceImpl();
	        EquipamientoDto dto = es.equiparArma(person.getId(), seleccionada.getId());
	        System.out.println("Arma equipada OK: " + dto.getNombre());
	    } catch (ReglaJuegoException e) {
	        System.out.println("No puedes equipar: " + e.getMessage());
	    }
	}
	
	private static void menuEscudos(Personaje person) {
	    List<Equipamiento> equipo = person.getEquipo();
	    if (equipo == null || equipo.isEmpty()) {
	        System.out.println("No llevas armas ni objetos");
	        return;
	    }

	    List<Escudos> escudos = new ArrayList<>();
	    for (Equipamiento e : equipo) {
	        if (e instanceof Escudos) escudos.add((Escudos) e);
	    }

	    if (escudos.isEmpty()) {
	        System.out.println("No tienes ningun escudo en el inventario");
	        return;
	    }

	    System.out.println("\n--- ESCUDOS ---");
	    for (int i = 0; i < escudos.size(); i++) {
	        Escudos e = escudos.get(i);
	        System.out.println((i + 1) + ") " + e.getNombre() +
	            " [id=" + e.getId() + "]" +
	            " Puntos Resistencia=" + e.getPuntosResistencia() +
	            " durabilidad=" + e.getDurabilidad() +
	            " nivelReq=" + e.getNivelRequerido());
	    }

	    System.out.println((escudos.size() + 1) + ") Volver");
	    int opcion = pideDatoNumerico("Elige un escudo para equipar:");

	    if (opcion < 1 || opcion > escudos.size()) {
	        System.out.println("Volviendo sin cambiar arma.");
	        return;
	    }

	    Escudos seleccionado = escudos.get(opcion - 1);

	    try {
	        EquipamientoService es = new EquipamientoServiceImpl();
	        EquipamientoDto dto = es.equiparEscudo(person.getId(), seleccionado.getId());
	        System.out.println("Escudo equipado OK: " + dto.getNombre());
	    } catch (ReglaJuegoException e) {
	        System.out.println("No puedes equipar: " + e.getMessage());
	    }
	}

	private static void menuConsumir(Personaje person) {
	    if (person == null || person.getId() == null) {
	        System.out.println("No hay personaje válido seleccionado.");
	        return;
	    }

	    try {
	        EquipamientoService es = new EquipamientoServiceImpl();

	        // 1) Pedimos al service los consumibles curativos (ya filtrados)
	        List<EquipamientoDto> curativos = es.listarConsumiblesCurativos(person.getId());

	        if (curativos == null || curativos.isEmpty()) {
	            System.out.println("No tienes consumibles curativos (Baya, CarneSeca o Pocion).");
	            return;
	        }

	        // 2) Pintamos menú
	        System.out.println("\n--- CONSUMIBLES CURATIVOS ---");
	        for (int i = 0; i < curativos.size(); i++) {
	            model.EquipamientoDto d = curativos.get(i);
	            System.out.println((i + 1) + ") " + d.getNombre() + " (id=" + d.getId() + ")");
	        }
	        System.out.println((curativos.size() + 1) + ") Volver");

	        int opcion = pideDatoNumerico("Elige un consumible: ");

	        if (opcion == curativos.size() + 1) {
	            return; // volver
	        }
	        if (opcion < 1 || opcion > curativos.size()) {
	            System.out.println("Opción no válida.");
	            return;
	        }

	        EquipamientoDto elegido = curativos.get(opcion - 1);

	        // 3) Consumir con service (cura + remove + persist)
	        int vidaAntes = person.getPuntosVida();
	        int vidaDespues = es.consumirCurativo(person.getId(), elegido.getId());

	        // 4) Actualizamos el objeto en memoria para que se vea al instante
	        person.setPuntosVida(vidaDespues);

	        System.out.println("Has consumido " + elegido.getNombre() +
	                ". Vida actual (PV/PVMax): " + vidaAntes + " -> " +
	                vidaDespues + "/" + person.getPuntosVidaMax());

	    } catch (exceptions.ReglaJuegoException e) {
	        System.out.println("No puedes consumir: " + e.getMessage());
	    } catch (RuntimeException e) {
	        System.out.println("Error general: " + e.getMessage());
	    }
	}

	private static void menuTirarObjetoAlaMierda(Personaje person) {
	    if (person == null || person.getId() == null) {
	        System.out.println("No hay personaje válido.");
	        return;
	    }

	    try {
	        Personaje rec = recargarPersonaje(person.getId());
	        List<Equipamiento> equipo = rec.getEquipo();

	        if (equipo == null || equipo.isEmpty()) {
	            System.out.println("No tienes nada que tirar.");
	            return;
	        }

	        System.out.println("\n--- TIRAR OBJETO  ---");
	        for (int i = 0; i < equipo.size(); i++) {
	            Equipamiento e = equipo.get(i);
	            System.out.println((i + 1) + ") " + e.getNombre()
	                    + " [id=" + e.getId() + "] durabilidad=" + e.getDurabilidad());
	        }
	        System.out.println((equipo.size() + 1) + ") Cancelar");

	        int opcion = pideDatoNumerico("Elige el objeto: ");
	        if (opcion < 1 || opcion > equipo.size()) {
	            System.out.println("Cancelado.");
	            return;
	        }

	        Equipamiento elegido = equipo.get(opcion - 1);
	        Long equipId = elegido.getId();
	        String nombreObj = elegido.getNombre();

	        EquipamientoService es = new EquipamientoServiceImpl();
	        es.eliminarDeInventario(person.getId(), equipId);


	        Personaje rec2 = recargarPersonaje(person.getId());
	        System.out.println("El objeto " + nombreObj + " se ha eliminado correctamente!");
	        person.setEquipo(rec2.getEquipo());

	    } catch (ReglaJuegoException e) {
	        System.out.println("No puedes tirar ese objeto: " + e.getMessage());
	    } catch (Exception e) {
	        System.out.println("Error inesperado al tirar objeto: " + e.getClass().getSimpleName() + " - " + e.getMessage());
	        e.printStackTrace();
	    }
	}

	public static int calcularPesoTotal(Personaje person) {
		int totalPeso = 0;
		if (person.getEquipo() != null) {
			for (Equipamiento e : person.getEquipo()) {
				totalPeso += e.getPeso();
			}
		}
		return totalPeso;
	}

	public static void mostrarEstado(Personaje person) {
		System.out.println("\n--- ESTADO DE " + person.getNombre() + " ---");
		System.out.println("Nivel: " + person.getNivel());
		System.out.println("Puntos de Vida: " + person.getPuntosVida() + "/" + person.getPuntosVidaMax());
		System.out.println("Puntos de Ataque: " + person.getPuntosAtaque());
		System.out.println("Peso total del inventario: " + calcularPesoTotal(person) + " unidades.");
	}

	public static void menuInventario(Personaje person) {
		if (person.getEquipo() == null) {
			person.setEquipo(new ArrayList<>());
		}

		boolean salirMenu = false;

		do {
			System.out.println("\n--- INVENTARIO DE " + person.getNombre() + " ---");
			System.out.println("---------------------------------------------");
			System.out.println("1. Ver estado del personaje");
			System.out.println("2. Ver todo el equipo");
			System.out.println("3. Ver armas / equipar arma");
			System.out.println("4. Ver escudos / equipar escudos");
			System.out.println("5. Consumir objeto (Baya / CarneSeca / Pocion)");
			System.out.println("6. Tirar objeto a la mierda");
			System.out.println("7. Mostrar criaturas aliadas");
			System.out.println("8. Volver");

			int opcion = pideDatoNumerico("Elige la opción deseada del inventario: ");

			switch (opcion) {
			case 1:
				mostrarEstado(person);
				break;
			case 2:
				mostrarEquipoCompleto(person);
				break;
			case 3:
				menuArmas(person);
				break;
			case 4:
				menuEscudos(person);
				break;
			case 5:
				menuConsumir(person);
				break;
			case 6:
				menuTirarObjetoAlaMierda(person);
				break;
			case 7:
				mostrarCompaneros(person);
				break;
			case 8:
				salirMenu = true;
				break;
			default:
				System.out.println("Opción no válida.");
			}
		} while (!salirMenu);
	}

	public static double pideDatoDecimal(String texto) {
		double numero = 0;
		boolean hayError;
		do {

			System.out.println(texto);
			Scanner scan = new Scanner(System.in);

			try {
				numero = scan.nextDouble();
				hayError = false;
			} catch (InputMismatchException ime) {
				hayError = true;
				System.out.println("Valor introducido no correcto");
			}

		} while (hayError);

		return numero;

	}

	public static String pideDatoCadena(String texto) {
		String dato = "";
		System.out.println(texto);
		Scanner scan = new Scanner(System.in);
		dato = scan.nextLine();

		return dato;
	}

	public static BigDecimal pideDatoBigDecimal(String texto) {

		try {
			System.out.println(texto);
			Scanner scan = new Scanner(System.in);
			BigDecimal numero = scan.nextBigDecimal();

			return numero;

		} catch (Exception e) {
			System.out.println("Error general " + e.getMessage());
			System.out.println("El dato introducido debe ser un número decimal (ej: 1234.56)");

			// Volvemos a preguntar recursivamente
			return pideDatoBigDecimal(texto);
		}
	}

	public static Personaje buscarBaya(Personaje personaje) {
	    if (personaje == null || personaje.getId() == null) {
	        System.out.println("Personaje no válido.");
	        return personaje;
	    }

	    EquipamientoService equipService = new EquipamientoServiceImpl();

	    int tirada = Utils.dadoDiez();

	    // baya venenosa -> solo cambia PV en memoria (se guarda al final del episodio)
	    if (tirada <= 3) {
	        System.out.println("Te comes una baya venenosa... Pierdes 5 de vida.");
	        personaje.setPuntosVida(personaje.getPuntosVida() - 5);
	        return personaje;
	    }

	    try {
	        if (tirada <= 7) {
	            System.out.println("Has encontrado algunas bayas");
	            equipService.añadirAlInventario(personaje.getId(), new Baya());
	        } else {
	            System.out.println("Has encontrado muchas bayas");
	            equipService.añadirAlInventario(personaje.getId(), new Baya());
	            equipService.añadirAlInventario(personaje.getId(), new Baya());
	        }

	        // recargar inventario actualizado
	        return recargarPersonaje(personaje.getId());

	    } catch (ReglaJuegoException e) {
	        System.out.println("No puedes añadir bayas: " + e.getMessage());
	        return personaje;
	    }
	}
	
	public static boolean fueUltimaCazaExitosa() {
		return ultimaCazaExitosa;
	}

	public static Personaje cazar(Personaje person) {
		ultimaCazaExitosa = false;
		
	    if (person == null || person.getId() == null) {
	        System.out.println("Personaje no válido.");
	        return person;
	    }

	    if (person.getCriaturas() == null || person.getCriaturas().isEmpty()) {
	        System.out.println("No puedes cazar sin un compañero criatura, primero invoca uno.");
	        return person;
	    }

	    EquipamientoService equipService = new EquipamientoServiceImpl();

	    boolean exito = dadoDiez() > 3; // 70%
	    Criatura presa = randomizarCriatura();

	    if (exito) {
	        boolean ganado = Utils.combate(person, presa);

	        if (ganado) {
	            ultimaCazaExitosa = true;
	            try {
	                System.out.println("Has cazado un " + presa.getNombre() + ", consigues carne seca.");
	                equipService.añadirAlInventario(person.getId(), new CarneSeca());
	                return recargarPersonaje(person.getId());
	            } catch (ReglaJuegoException e) {
	                System.out.println("No puedes añadir carne seca: " + e.getMessage());
	                return person;
	            }
	        } else {
	            return person;
	        }

	    } else {
	        int danioHecho = presa.atacar(person);
	        person.setPuntosVida(person.getPuntosVida() - danioHecho);
	        System.out.println("La presa te hace " + danioHecho + " de daño y huyes llorando.");
	        return person;
	    }
	}

	public static Personaje buscarObjeto(Personaje personaje) {
	    if (personaje == null || personaje.getId() == null) {
	        System.out.println("Personaje no válido.");
	        return personaje;
	    }

	    EquipamientoService equipService = new EquipamientoServiceImpl();

	    int tirada = Utils.dadoDiez();

	    // Caso malo: serpiente
	    if (tirada <= 2) {
	        System.out.println("Metes la mano en un agujero... es un nido de serpiente y te muerde.");
	        personaje.setPuntosVida(personaje.getPuntosVida() - 5);
	        return personaje; // esto se guarda al final del episodio por EpisodioService
	    }

	    // Caso bueno: encontramos un material
	    int tirada2 = Utils.dadoDiez();

	    String nombreEncontrado = null;
	    
	    try {
	        if (tirada2 == 1 || tirada2 == 2) {
	        	nombreEncontrado = "Mojon Seco";
	            System.out.println("Encuentras un objeto muy útil: Mojon Seco");
	            equipService.añadirAlInventario(personaje.getId(), new MojonSeco());
	        } else if (tirada2 == 3 || tirada2 == 4) {
	        	nombreEncontrado = "Cuerda";
	            System.out.println("Encuentras un objeto muy útil: Cuerda");
	            equipService.añadirAlInventario(personaje.getId(), new Cuerda());
	        } else if (tirada2 == 5 || tirada2 == 6) {
	        	nombreEncontrado = "Piedra";
	            System.out.println("Encuentras un objeto muy útil: Piedra");
	            equipService.añadirAlInventario(personaje.getId(), new Piedra());
	        } else if (tirada2 == 7 || tirada2 == 8) {
	        	nombreEncontrado = "Palo";
	            System.out.println("Encuentras un objeto muy útil: Palo");
	            equipService.añadirAlInventario(personaje.getId(), new Palo());
	        } else {
	        	nombreEncontrado = "Hoja Para Limpiar";
	            System.out.println("Encuentras un objeto muy útil: Hoja Para Limpiar");
	            equipService.añadirAlInventario(personaje.getId(), new HojaParaLimpiar());
	        }

	        // IMPORTANTÍSIMO: recargamos desde BD para traer el inventario actualizado
	        Personaje rec =  Utils.recargarPersonaje(personaje.getId());
	        System.out.println("\nHas encontrado el objeto: " + nombreEncontrado);
	        
	        return rec;

	    } catch (ReglaJuegoException e) {
	        System.out.println("No puedes añadir el objeto al inventario: " + e.getMessage());
	        return personaje;
	    }
	}

	public static void menuFabricar(Personaje personaje) {
	    if (personaje == null || personaje.getId() == null) {
	        System.out.println("Debes tener un personaje válido.");
	        return;
	    }

	    catalogoFabricacionArmasEscudos();
	    
	    String tipo = pideDatoCadena(
	        "/n¿Qué quieres fabricar? Escribe la que desees (ARCO, BUMERAN, CAZAMARIPOSAS, LANZA, HONDA, CAÑA PESCA, TRAMPA, ESCUDO MADERA, ESCUDO PIEDRA)"
	    );

	    try {
	        EquipamientoService es = new EquipamientoServiceImpl();
	        EquipamientoDto dto = es.fabricar(personaje.getId(), tipo);

	        System.out.println("Fabricado OK: " + dto.getNombre() +
	            " | durabilidad=" + dto.getDurabilidad() +
	            " | nivel requerido=" + dto.getNivelRequerido());
	    } catch (ReglaJuegoException e) {
	        System.out.println("No puedes fabricar: " + e.getMessage());
	    }
	}
	
	public static Personaje recargarPersonaje(Long personajeId) {
	    PersonajeDao personajeDao = new PersonajeDaoImpl();
	    return personajeDao.findByIdFetchAll(personajeId);
	}
	
	public static void catalogoFabricacionArmasEscudos() {

	    Arco arco = new Arco();
	    Bumeran bumeran = new Bumeran();
	    CanaPescar cana = new CanaPescar();
	    Honda honda = new Honda();
	    Lanza lanza = new Lanza();
	    Trampa trampa = new Trampa();
	    Cazamariposas caz = new Cazamariposas();
	    EscudoMadera emad = new EscudoMadera();
	    EscudoPiedra epie = new EscudoPiedra();
	    // ... añade el resto

	    System.out.println("\n=== CATÁLOGO DE ARMAS/ESCUDOS ===");

	    imprimirArma(arco);
	    imprimirArma(bumeran);
	    imprimirArma(cana);
	    imprimirArma(honda);
	    imprimirArma(lanza);
	    imprimirArma(trampa);
	    imprimirArma(caz);
	    imprimirEscudo(emad);
	    imprimirEscudo(epie);
	}
	
	private static void imprimirArma(Armas a) {
	    System.out.println(
	        "- " + a.getNombre()
	        + " | NivelReq: " + a.getNivelRequerido()
	        + " | Daño: " + a.getPuntosDaño()
	        + " | Durabilidad: " + a.getDurabilidad()
	        + " | Peso: " + a.getPeso()
	        + " | TipoDaño: " + a.getTipoDaño()
	        + " | Alcance: " + a.getAlcance()
	        + " | Precisión: " + a.getPrecision()
	        + " | Crit%: " + a.getProbCritico()
	    );
	}
	
	private static void imprimirEscudo(Escudos e) {
	    System.out.println(
	        "- " + e.getNombre()
	        + " | NivelReq: " + e.getNivelRequerido()
	        + " | Durabilidad: " + e.getDurabilidad()
	        + " | Peso: " + e.getPeso()
	        + " | Puntos Resistencia: " + e.getPuntosResistencia()
	    );
	}

}
