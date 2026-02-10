package es.cursojava.springbootrol.utilidades;

import java.math.BigDecimal;
import java.util.ArrayList;
import java.util.InputMismatchException;
import java.util.List;
import java.util.Scanner;
import java.util.Set;
import java.util.concurrent.ThreadLocalRandom;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.criatura.Conejo;
import es.cursojava.springbootrol.entities.criatura.Criatura;
import es.cursojava.springbootrol.entities.criatura.Gusano;
import es.cursojava.springbootrol.entities.criatura.Jabali;
import es.cursojava.springbootrol.entities.criatura.Lobo;
import es.cursojava.springbootrol.entities.criatura.Mosquito;
import es.cursojava.springbootrol.entities.criatura.Raton;
import es.cursojava.springbootrol.entities.episodios.AccionesEpisodio;
import es.cursojava.springbootrol.entities.equipo.Equipamiento;
import es.cursojava.springbootrol.entities.equipo.armas.Arco;
import es.cursojava.springbootrol.entities.equipo.armas.Armas;
import es.cursojava.springbootrol.entities.equipo.armas.Bumeran;
import es.cursojava.springbootrol.entities.equipo.armas.CanaPescar;
import es.cursojava.springbootrol.entities.equipo.armas.Cazamariposas;
import es.cursojava.springbootrol.entities.equipo.armas.Honda;
import es.cursojava.springbootrol.entities.equipo.armas.Lanza;
import es.cursojava.springbootrol.entities.equipo.armas.Trampa;
import es.cursojava.springbootrol.entities.equipo.escudos.EscudoMadera;
import es.cursojava.springbootrol.entities.equipo.escudos.EscudoPiedra;
import es.cursojava.springbootrol.entities.equipo.escudos.Escudos;
import es.cursojava.springbootrol.entities.equipo.objetos.Baya;
import es.cursojava.springbootrol.entities.equipo.objetos.CarneSeca;
import es.cursojava.springbootrol.entities.equipo.objetos.Cuerda;
import es.cursojava.springbootrol.entities.equipo.objetos.HojaParaLimpiar;
import es.cursojava.springbootrol.entities.equipo.objetos.MojonSeco;
import es.cursojava.springbootrol.entities.equipo.objetos.Palo;
import es.cursojava.springbootrol.entities.equipo.objetos.Piedra;
import es.cursojava.springbootrol.entities.equipo.objetos.Pocion;
import es.cursojava.springbootrol.exceptions.ReglaJuegoException;
import es.cursojava.springbootrol.model.CriaturaDto;
import es.cursojava.springbootrol.model.EquipamientoDto;
import es.cursojava.springbootrol.service.EquipamientoService;
import es.cursojava.springbootrol.service.impl.EquipamientoServiceImpl;


public class JuegoActions {

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



//	public static Criatura invocacionCompañeroCriatura(Personaje person) throws ReglaJuegoException {
//
//	    if (person == null || person.getId() == null) {
//	        throw new ReglaJuegoException("Personaje no válido o no persistido.");
//	    }
//
//	    Criatura compiRandom = randomizarCriatura();
//
//	    boolean ok = dadoDiez() > 1;
//	    if (!ok) {
//	        System.out.println("No estás pensando en lo que debes, la criatura se ríe de ti y te ataca.");
//	        person.setPuntosVida(person.getPuntosVida() - compiRandom.getPuntosAtaque());
//	        System.out.println("Te ha quitado " + compiRandom.getPuntosAtaque() + " puntos de vida, te quedan "
//	                + person.getPuntosVida() + " puntos de vida.");
//	        throw new ReglaJuegoException("La invocación ha fallado.");
//	    }
//
//	    // alias automático = nombre de la criatura
//	    String nombreCriatura = compiRandom.getClass().getSimpleName();
//	    compiRandom.setNombre(nombreCriatura);
//	    compiRandom.setAlias(nombreCriatura);
//
//	    // id interno opcional
//	   // compiRandom.setId(generarIdInterno());
//
//	   // person.addCriatura(compiRandom);
//	    person.getCriaturas().add(compiRandom);
//	    System.out.println("Has invocado una criatura: " 
//	            + compiRandom.getNombre() 
//	            + " alias=" + compiRandom.getAlias());
//
//	    return compiRandom;
//	}


//	public static Criatura invocacionCompañeroCriaturaPersistente(Personaje person) {
//
//		if (person == null || person.getId() == null) {
//			System.out.println("Error: personaje no válido o no persistido.");
//			return null;
//		}
//
//		// narrativa: “qué sale”
//		Criatura compiRandom = randomizarCriatura();
//
//		// tirada (90% éxito)
//		boolean ok = dadoDiez() > 1;
//		if (!ok) {
//			System.out.println("No estás pensando en lo que debes, la criatura se ríe de ti y te ataca.");
//			person.setPuntosVida(person.getPuntosVida() - compiRandom.getPuntosAtaque());
//			System.out.println("Te ha quitado " + compiRandom.getPuntosAtaque() + " puntos de vida, te quedan "
//					+ person.getPuntosVida() + " puntos de vida.");
//			return null;
//		}
//
//		System.out.println("Ahora tienes un compañero de viaje, ¿quieres ponerle un alias?:");
//		String alias = pideDatoCadena("Introduce el alias deseado: ");
//
//		// tipo para el service (MOSQUITO/CONEJO/...)
//		String tipo = compiRandom.getClass().getSimpleName().toUpperCase();
//
//		try {
//			CriaturaDto dto = criaturaService.invocarCompanero(person.getId(), tipo, alias);
//
//			System.out.println("Has invocado una criatura: " + dto.getTipo() + " alias=" + dto.getAlias());
//
//			// mantener coherencia en memoria también:
//			compiRandom.setId(dto.getId());
//			compiRandom.setNombre(dto.getNombre());
//			compiRandom.setAlias(dto.getAlias());
//			compiRandom.setNivel(dto.getNivel());
//			compiRandom.setExperiencia(dto.getExperiencia());
//			compiRandom.setPuntosVida(dto.getPuntosVida());
//			compiRandom.setPuntosAtaque(dto.getPuntosAtaque());
//			person.addCriatura(compiRandom);
//
//			return compiRandom;
//
//		} catch (ReglaJuegoException e) {
//			System.out.println("No se pudo invocar: " + e.getMessage());
//			return null;
//		}
//	}
	
	public static Criatura invocacionCompañeroCriatura(Personaje person) throws ReglaJuegoException {

	    if (person == null) throw new ReglaJuegoException("Personaje no válido.");

	    Criatura compi = randomizarCriatura();

	    boolean ok = dadoDiez() > 1;
	    if (!ok) {
	        person.setPuntosVida(person.getPuntosVida() - compi.getPuntosAtaque());
	        throw new ReglaJuegoException("La invocación ha fallado.");
	    }

	    String nombre = compi.getClass().getSimpleName();
	    compi.setNombre(nombre);
	    compi.setAlias(nombre);

	    // IMPORTANTÍSIMO: usar helper del personaje
	    person.addCriatura(compi);

	    return compi;
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

	public static void invocarTodasCriaturas(Personaje person) {

		if (person == null || person.getId() == null) {
			System.out.println("Error: personaje no válido o no persistido.");
			return;
		}

		// 🔒 Lista local de criaturas permitidas
		Set<String> criaturasPermitidas = Set.of("CONEJO", "GUSANO", "JABALI", "LOBO", "MOSQUITO",
				"PEZ_PREISTORICO_GIGANTE", "RATON", "SILURO");

		Lobo lobo = new Lobo();
		Jabali jabali = new Jabali();

		int tirada = dadoDiez();

		if (tirada == 1) {
			System.out.println(
					"Mientras invocas al lobo un mosquito te pica y te distraes, el lobo se enfada y te ataca.");
			combate(person, lobo);
			return;
		}

		if (tirada == 9) {
			System.out.println(
					"Mientras invocas al jabalí un ratón te asusta y te distraes, el jabalí se enfada y te ataca.");
			combate(person, jabali);
			return;
		}

		String tipo;
		String nombreDefault;

		if (tirada > 1 && tirada < 5) {
			tipo = "LOBO";
			nombreDefault = "Lobo";
			System.out.println("Has invocado correctamente a un lobo.");
		} else {
			tipo = "JABALI";
			nombreDefault = "Jabali";
			System.out.println("Has invocado correctamente a un jabalí.");
		}

		// ✅ Validación final
		if (!criaturasPermitidas.contains(tipo)) {
			System.out.println("Error: criatura no permitida por las reglas del juego.");
			return;
		}

		String alias = pideDatoCadena("¿Quieres ponerle un alias? (Enter para dejar el nombre): ");
		if (alias == null || alias.trim().isEmpty()) {
			alias = nombreDefault;
		}

		try {
			CriaturaDto dto = criaturaService.invocarCompanero(person.getId(), tipo, alias);

			System.out.println("Criatura guardada en BD: " + dto.getTipo() + " alias=" + dto.getAlias());

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
				System.out.println((i + 1) + ") " + d.getNombre() + " [id=" + d.getId() + "]" + " durabilidad="
						+ d.getDurabilidad());
			}
			System.out.println((curativos.size() + 1) + ") Cancelar");

			int opcion = pideDatoNumerico("Elige: ");
			if (opcion < 1 || opcion > curativos.size())
				return false;

			EquipamientoDto elegido = curativos.get(opcion - 1);

			int antes = person.getPuntosVida();
			int despues = es.consumirCurativo(person.getId(), elegido.getId());

			// IMPORTANTE: actualizar el objeto Personaje en memoria para que el combate
			// muestre PV correcto
			person.setPuntosVida(despues);

			System.out.println("Has consumido " + elegido.getNombre() + ". PV: " + antes + " -> " + despues + " / "
					+ person.getPuntosVidaMax());

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
	public static void invocarLoboJabali(Personaje person, AccionesEpisodio acciones) {

	    if (person == null) {
	        acciones.add("Error: personaje no válido.");
	        return;
	    }

	    int tirada = JuegoActions.dadoDiez();

	    // Fallo crítico invocando lobo
	    if (tirada == 1) {
	        acciones.add("Mientras invocas al lobo, un mosquito te pica y te distrae. "
	                + "El lobo se enfada y te ataca.");
	        JuegoActions.combateAuto(person, new Lobo(), acciones);
	        return;
	    }

	    // Fallo crítico invocando jabalí
	    if (tirada == 9) {
	        acciones.add("Mientras invocas al jabalí, un ratón te asusta y te distrae. "
	                + "El jabalí se enfada y te ataca.");
	        JuegoActions.combateAuto(person, new Jabali(), acciones);
	        return;
	    }

	    // Éxito: decidir criatura según tirada
	    if (tirada > 1 && tirada < 5) {

	        acciones.add("Has invocado correctamente a un lobo.");

	        Lobo nuevo = new Lobo();
	        nuevo.setNombre("Lobo");
	        person.getCriaturas().add(nuevo);

	        acciones.add("Tu nuevo compañero lobo se llama: Lobo");

	    } else {

	        acciones.add("Has invocado correctamente a un jabalí.");

	        Jabali nuevo = new Jabali();
	        nuevo.setNombre("Jabalí");
	        person.getCriaturas().add(nuevo);

	        acciones.add("Tu nuevo compañero jabalí se llama: Jabalí");
	    }

	    acciones.add("La invocación ha sido un éxito.");
	}


	public static void invocarLoboJabaliPersistido(Personaje person, AccionesEpisodio acciones) {

		if (person == null || person.getId() == null) {
			System.out.println("Error: personaje no válido o no persistido.");
			return;
		}

		Lobo lobo = new Lobo();
		Jabali jabali = new Jabali();

		int tirada = dadoDiez();

		if (tirada == 1) {
			System.out.println(
					"Mientras invocas al lobo un mosquito te pica y te distraes, el lobo se enfada y te ataca.");
			combate(person, lobo);
			return;
		}

		if (tirada == 9) {
			System.out.println(
					"Mientras invocas al jabalí un ratón te asusta y te distraes, el jabalí se enfada y te ataca.");
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

			// Muy importante: sincronizar el objeto en memoria para que el episodio lo vea
			// al instante
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

	public static void recuperarVida(Personaje personaje, AccionesEpisodio acciones) {

		personaje.setPuntosVida(personaje.getPuntosVidaMax());
		acciones.add("Has recuperado toda tu vida. PV: " + personaje.getPuntosVida() + "/" + personaje.getPuntosVidaMax());

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
				System.out.println("Criatura: " + c.getNombre() + " | Tipo: " + c.getTipo() + " | Alias: "
						+ c.getAlias() + " | PV: " + c.getPuntosVida() + " | ATQ: " + c.getPuntosAtaque());
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
			if (e instanceof Armas)
				armas.add((Armas) e);
		}

		if (armas.isEmpty()) {
			System.out.println("No tienes ninguna arma en el inventario");
			return;
		}

		System.out.println("\n--- ARMAS ---");
		for (int i = 0; i < armas.size(); i++) {
			Armas a = armas.get(i);
			System.out.println((i + 1) + ") " + a.getNombre() + " [id=" + a.getId() + "]" + " daño=" + a.getPuntosDaño()
					+ " durabilidad=" + a.getDurabilidad() + " nivelReq=" + a.getNivelRequerido());
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
			if (e instanceof Escudos)
				escudos.add((Escudos) e);
		}

		if (escudos.isEmpty()) {
			System.out.println("No tienes ningun escudo en el inventario");
			return;
		}

		System.out.println("\n--- ESCUDOS ---");
		for (int i = 0; i < escudos.size(); i++) {
			Escudos e = escudos.get(i);
			System.out.println((i + 1) + ") " + e.getNombre() + " [id=" + e.getId() + "]" + " Puntos Resistencia="
					+ e.getPuntosResistencia() + " durabilidad=" + e.getDurabilidad() + " nivelReq="
					+ e.getNivelRequerido());
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
				es.cursojava.springbootrol.model.EquipamientoDto d = curativos.get(i);
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

			System.out.println("Has consumido " + elegido.getNombre() + ". Vida actual (PV/PVMax): " + vidaAntes
					+ " -> " + vidaDespues + "/" + person.getPuntosVidaMax());

		} catch (es.cursojava.springbootrol.exceptions.ReglaJuegoException e) {
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
				System.out.println(
						(i + 1) + ") " + e.getNombre() + " [id=" + e.getId() + "] durabilidad=" + e.getDurabilidad());
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
			System.out.println(
					"Error inesperado al tirar objeto: " + e.getClass().getSimpleName() + " - " + e.getMessage());
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

	public static Personaje buscarBaya(Personaje personaje, AccionesEpisodio acciones) {
	    if (personaje == null) return null;

	    int tirada = JuegoActions.dadoDiez();

	    if (tirada <= 3) {
	        personaje.setPuntosVida(personaje.getPuntosVida() - 5);
	        acciones.add("Te comes una baya venenosa y pierdes 5 PV.");
	        return personaje;
	    }

	    int cantidad = (tirada <= 7) ? 1 : 2;

	    for (int i = 0; i < cantidad; i++) {
	        personaje.addEquipamiento(new Baya());
	        acciones.add("Has conseguido una Baya!");
	    }

	    personaje.ganarExperiencia(10, acciones);
	    acciones.add("Ganas 10 EXP por recolectar bayas.");

	    return personaje;
	}

	public static boolean fueUltimaCazaExitosa() {
		return ultimaCazaExitosa;
	}

	public static void cazar(Personaje person, AccionesEpisodio acciones) {
	    ultimaCazaExitosa = false;

	    if (person == null) {
	        acciones.add("Error: personaje no válido.");
	        return;
	    }

	    if (person.getCriaturas() == null || person.getCriaturas().isEmpty()) {
	        acciones.add("No puedes cazar sin un compañero criatura. Primero invoca uno.");
	        return;
	    }

	    boolean hayEncuentro = dadoDiez() > 3; 
	    Criatura presa = randomizarCriatura();
	    presa.setNombre(presa.getClass().getSimpleName());

	    if (!hayEncuentro) {
	        int danio = presa.atacar(person);
	        person.setPuntosVida(Math.max(0, person.getPuntosVida() - danio));
	        acciones.add("Intentaste cazar pero fallaste. La presa te hizo " + danio + " de daño.");
	        return;
	    }

	    acciones.add("¡Encuentras una presa! Aparece un " + presa.getNombre() + ".");

	    boolean ganado = combateAuto(person, presa, acciones);

	    if (ganado) {
	        ultimaCazaExitosa = true;
	        person.addEquipamiento(new CarneSeca()); 
	        acciones.add("Buena caza!! Obtienes Carne Seca.");
	    } else {
	        acciones.add("La caza salió mal. No consigues carne.");
	    }
	}
	
	public static boolean combateAuto(Personaje p, Criatura enemigo, AccionesEpisodio acciones) {
	    if (!p.tieneArmaEquipada()) {
	        p.setPuntosVida(1);
	        acciones.add("No tenías arma equipada. El enemigo te deja a 1 PV y huyes.");
	        return false;
	    }
	    if (p.getCriaturas() == null || p.getCriaturas().isEmpty()) {
	        acciones.add("No puedes combatir sin compañero criatura.");
	        return false;
	    }

	    int turnos = 0;
	    while (p.estaVivo() && enemigo.estaVivo() && turnos < 30) {

	        int d1 = p.atacar(enemigo);
	        acciones.add("Atacas a " + enemigo.getNombre() + " y le haces " + d1 + " de daño.");

	        Criatura compi = obtenerCompaneroActivo(p);
	        if (enemigo.estaVivo() && compi != null) {
	            int d2 = compi.atacar(enemigo);
	            acciones.add("Tu compañero " + compi.getAlias() + " ataca e inflige " + d2 + " de daño.");
	        }

	        if (enemigo.estaVivo()) {
	            int d3 = enemigo.atacar(p);
	            acciones.add(enemigo.getNombre() + " te ataca y te hace " + d3 + " de daño. PV: " + p.getPuntosVida());
	        }

	        turnos++;
	    }

	    if (!enemigo.estaVivo()) {
	        acciones.add("¡Has ganado el combate!");
	        p.ganarExperiencia(40, acciones);          
	        acciones.add("Ganas 40 EXP.");
	        return true;
	    } else {
	        acciones.add("Has perdido el combate (o se alargó demasiado y escapaste).");
	        return false;
	    }
	}
	
	public static String buscarObjeto(Personaje personaje, AccionesEpisodio acciones) {
	    if (personaje == null) return "Error: personaje no válido.";

	    int tirada = dadoDiez();

	    if (tirada <= 2) {
	        personaje.setPuntosVida(personaje.getPuntosVida() - 5);
	        acciones.add("Metiste la mano en un agujero... era una serpiente. Pierdes 5 PV.");
	        return "Metiste la mano en un agujero... era una serpiente. Pierdes 5 PV.";
	    }

	    int tirada2 = dadoDiez();
	    String msg;

	    if (tirada2 <= 2) {
	        personaje.addEquipamiento(new MojonSeco());
	        msg = "Encontraste un Mojón Seco.";
	    } else if (tirada2 <= 4) {
	        personaje.addEquipamiento(new Cuerda());
	        msg = "Encontraste una Cuerda.";
	    } else if (tirada2 <= 6) {
	        personaje.addEquipamiento(new Piedra());
	        msg = "Encontraste una Piedra.";
	    } else if (tirada2 <= 8) {
	        personaje.addEquipamiento(new Palo());
	        msg = "Encontraste un Palo.";
	    } else {
	        personaje.addEquipamiento(new HojaParaLimpiar());
	        msg = "Encontraste una Hoja Para Limpiar.";
	    }

	    personaje.ganarExperiencia(20, acciones);
	    acciones.add("Ganas 20 EXP por explorar.");

	    return msg;
	}



//	public static Personaje buscarObjeto(Personaje personaje) {
//
//	    if (personaje == null) {
//	        System.out.println("Personaje no válido.");
//	        return personaje;
//	    }
//
//	    int tirada = Utils.dadoDiez();
//
//	    // Caso malo: serpiente
//	    if (tirada <= 2) {
//	        System.out.println("Metes la mano en un agujero... es un nido de serpiente y te muerde.");
//	        personaje.setPuntosVida(personaje.getPuntosVida() - 5);
//	        return personaje;
//	    }
//
//	    // Caso bueno: encontramos un material
//	    int tirada2 = Utils.dadoDiez();
//	    String nombreEncontrado;
//
//	    if (tirada2 == 1 || tirada2 == 2) {
//	        nombreEncontrado = "Mojon Seco";
//	        System.out.println("Encuentras un objeto muy útil: Mojon Seco");
//	        personaje.getEquipo().add(new MojonSeco());
//
//	    } else if (tirada2 == 3 || tirada2 == 4) {
//	        nombreEncontrado = "Cuerda";
//	        System.out.println("Encuentras un objeto muy útil: Cuerda");
//	        personaje.getEquipo().add(new Cuerda());
//
//	    } else if (tirada2 == 5 || tirada2 == 6) {
//	        nombreEncontrado = "Piedra";
//	        System.out.println("Encuentras un objeto muy útil: Piedra");
//	        personaje.getEquipo().add(new Piedra());
//
//	    } else if (tirada2 == 7 || tirada2 == 8) {
//	        nombreEncontrado = "Palo";
//	        System.out.println("Encuentras un objeto muy útil: Palo");
//	        personaje.getEquipo().add(new Palo());
//
//	    } else {
//	        nombreEncontrado = "Hoja Para Limpiar";
//	        System.out.println("Encuentras un objeto muy útil: Hoja Para Limpiar");
//	        personaje.getEquipo().add(new HojaParaLimpiar());
//	    }
//
//	    System.out.println("\nHas encontrado el objeto: " + nombreEncontrado);
//	    return personaje;
//	}

//	public static Personaje buscarObjetoPersistido(Personaje personaje) {
//		if (personaje == null || personaje.getId() == null) {
//			System.out.println("Personaje no válido.");
//			return personaje;
//		}
//
//		EquipamientoService equipService = new EquipamientoServiceImpl();
//
//		int tirada = JuegoActions.dadoDiez();
//
//		// Caso malo: serpiente
//		if (tirada <= 2) {
//			System.out.println("Metes la mano en un agujero... es un nido de serpiente y te muerde.");
//			personaje.setPuntosVida(personaje.getPuntosVida() - 5);
//			return personaje; // esto se guarda al final del episodio por EpisodioService
//		}
//
//		// Caso bueno: encontramos un material
//		int tirada2 = JuegoActions.dadoDiez();
//
//		String nombreEncontrado = null;
//
//		try {
//			if (tirada2 == 1 || tirada2 == 2) {
//				nombreEncontrado = "Mojon Seco";
//				System.out.println("Encuentras un objeto muy útil: Mojon Seco");
//				equipService.añadirAlInventario(personaje.getId(), new MojonSeco());
//			} else if (tirada2 == 3 || tirada2 == 4) {
//				nombreEncontrado = "Cuerda";
//				System.out.println("Encuentras un objeto muy útil: Cuerda");
//				equipService.añadirAlInventario(personaje.getId(), new Cuerda());
//			} else if (tirada2 == 5 || tirada2 == 6) {
//				nombreEncontrado = "Piedra";
//				System.out.println("Encuentras un objeto muy útil: Piedra");
//				equipService.añadirAlInventario(personaje.getId(), new Piedra());
//			} else if (tirada2 == 7 || tirada2 == 8) {
//				nombreEncontrado = "Palo";
//				System.out.println("Encuentras un objeto muy útil: Palo");
//				equipService.añadirAlInventario(personaje.getId(), new Palo());
//			} else {
//				nombreEncontrado = "Hoja Para Limpiar";
//				System.out.println("Encuentras un objeto muy útil: Hoja Para Limpiar");
//				equipService.añadirAlInventario(personaje.getId(), new HojaParaLimpiar());
//			}
//
//			// IMPORTANTÍSIMO: recargamos desde BD para traer el inventario actualizado
//			Personaje rec = JuegoActions.recargarPersonaje(personaje.getId());
//			System.out.println("\nHas encontrado el objeto: " + nombreEncontrado);
//
//			return rec;
//
//		} catch (ReglaJuegoException e) {
//			System.out.println("No puedes añadir el objeto al inventario: " + e.getMessage());
//			return personaje;
//		}
//	}

//	public static void menuFabricar(Personaje personaje) {
//		if (personaje == null || personaje.getId() == null) {
//			System.out.println("Debes tener un personaje válido.");
//			return;
//		}
//
//		catalogoFabricacionArmasEscudos();
//
//		String tipo = pideDatoCadena(
//				"/n¿Qué quieres fabricar? Escribe la que desees (ARCO, BUMERAN, CAZAMARIPOSAS, LANZA, HONDA, CAÑA PESCA, TRAMPA, ESCUDO MADERA, ESCUDO PIEDRA)");
//
//		try {
//			EquipamientoService es = new EquipamientoServiceImpl();
//			EquipamientoDto dto = es.fabricar(personaje.getId(), tipo);
//
//			System.out.println("Fabricado OK: " + dto.getNombre() + " | durabilidad=" + dto.getDurabilidad()
//					+ " | nivel requerido=" + dto.getNivelRequerido());
//		} catch (ReglaJuegoException e) {
//			System.out.println("No puedes fabricar: " + e.getMessage());
//		}
//	}
	
	public static void fabricarArmaAleatoria(Personaje p, AccionesEpisodio acciones) {
	    if (p == null) {
	        acciones.add("Error: personaje no válido.");
	        return;
	    }
	    if (p.getEquipo() == null) p.setEquipo(new java.util.ArrayList<>());

	    final int INTENTOS = 5;

	    for (int i = 0; i < INTENTOS; i++) {
	        Armas arma = randomizarArma();
	        Receta receta = recetaParaArma(arma);

	        boolean nivelOk = p.getNivel() >= receta.nivelReq;
	        boolean matsOk = tieneMateriales(p, receta.materiales);

	        if (nivelOk && matsOk) {
	            consumirMateriales(p, receta.materiales);
	            p.addEquipamiento(arma); // IMPORTANTE: usa vuestro addEquipamiento (setPersonaje + add)
	            acciones.add("Fabricas " + arma.getNombre() + " (nivel req " + receta.nivelReq + ").");
	            if (receta.materiales.length > 0) {
	                acciones.add("Consumes materiales: " + String.join(", ", receta.materiales) + ".");
	            }
	            return;
	        }
	    }

	    // si llega aquí, no ha podido fabricar
	    acciones.add("Intentaste fabricar, pero no tienes nivel/materiales suficientes para ninguna receta.");
	}
	
	private static Armas randomizarArma() {
	    int tirada = ThreadLocalRandom.current().nextInt(1, 8);
	    return switch (tirada) {
	        case 1 -> new Arco();
	        case 2 -> new Bumeran();
	        case 3 -> new CanaPescar();
	        case 4 -> new Cazamariposas();
	        case 5 -> new Honda();
	        case 6 -> new Lanza();
	        default -> new Trampa();
	    };
	}

	// ======= HELPERS FABRICACIÓN (SOLO MEMORIA) =======

	private static class Receta {
	    final int nivelReq;
	    final String[] materiales;

	    Receta(int nivelReq, String... materiales) {
	        this.nivelReq = nivelReq;
	        this.materiales = materiales;
	    }
	}

	private static Receta recetaParaArma(Armas arma) {
	    if (arma instanceof Arco)         return new Receta(3, "PALO", "CUERDA");
	    if (arma instanceof Bumeran)      return new Receta(1, "PALO");
	    if (arma instanceof Cazamariposas)return new Receta(1, "PALO", "MOJON_SECO");
	    if (arma instanceof Lanza)        return new Receta(2, "PALO", "PIEDRA");
	    if (arma instanceof Honda)        return new Receta(1, "CUERDA");
	    if (arma instanceof CanaPescar)   return new Receta(2, "CUERDA", "PALO", "BAYA");
	    if (arma instanceof Trampa)       return new Receta(3, "CUERDA", "PALO", "PIEDRA");

	    // Por si metéis más armas en el futuro:
	    return new Receta(1);
	}

	private static boolean tieneMateriales(Personaje p, String... materiales) {
	    if (materiales == null || materiales.length == 0) return true;
	    if (p == null || p.getEquipo() == null) return false;

	    // Contar por “tipo material” porque puede haber materiales repetidos
	    java.util.Map<String, Integer> needed = new java.util.HashMap<>();
	    for (String m : materiales) {
	        String key = normalizaMaterial(m);
	        needed.put(key, needed.getOrDefault(key, 0) + 1);
	    }

	    // Contar lo que tengo
	    java.util.Map<String, Integer> have = new java.util.HashMap<>();
	    for (Equipamiento e : p.getEquipo()) {
	        String tipo = tipoMaterial(e);
	        if (tipo != null) {
	            have.put(tipo, have.getOrDefault(tipo, 0) + 1);
	        }
	    }

	    // Ver si cumplo
	    for (var entry : needed.entrySet()) {
	        int tengo = have.getOrDefault(entry.getKey(), 0);
	        if (tengo < entry.getValue()) return false;
	    }
	    return true;
	}

	private static void consumirMateriales(Personaje p, String... materiales) {
	    if (materiales == null || materiales.length == 0) return;
	    for (String m : materiales) {
	        eliminarUno(p, m);
	    }
	}

	private static void eliminarUno(Personaje p, String material) {
	    if (p == null || p.getEquipo() == null) return;
	    String target = normalizaMaterial(material);

	    // buscamos el primer objeto que haga match y lo quitamos
	    for (int i = 0; i < p.getEquipo().size(); i++) {
	        Equipamiento e = p.getEquipo().get(i);
	        if (target.equals(tipoMaterial(e))) {
	            p.getEquipo().remove(i);
	            return;
	        }
	    }
	}

	private static String normalizaMaterial(String m) {
	    if (m == null) return "";
	    return m.trim().toUpperCase().replace('Á', 'A').replace('É','E').replace('Í','I').replace('Ó','O').replace('Ú','U');
	}

	/**
	 * Devuelve el “tipo material” (PALO, CUERDA, PIEDRA, BAYA, MOJON_SECO) si es un material.
	 * Si no es material, devuelve null.
	 */
	private static String tipoMaterial(Equipamiento e) {
	    if (e == null) return null;

	    if (e instanceof Palo)      return "PALO";
	    if (e instanceof Cuerda)    return "CUERDA";
	    if (e instanceof Piedra)    return "PIEDRA";
	    if (e instanceof Baya)      return "BAYA";
	    if (e instanceof MojonSeco) return "MOJON_SECO";

	    // Si queréis que HojaParaLimpiar cuente como “material”, añadidlo aquí.
	    // if (e instanceof HojaParaLimpiar) return "HOJA_PARA_LIMPIAR";

	    return null;
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
		System.out.println("- " + a.getNombre() + " | NivelReq: " + a.getNivelRequerido() + " | Daño: "
				+ a.getPuntosDaño() + " | Durabilidad: " + a.getDurabilidad() + " | Peso: " + a.getPeso()
				+ " | TipoDaño: " + a.getTipoDaño() + " | Alcance: " + a.getAlcance() + " | Precisión: "
				+ a.getPrecision() + " | Crit%: " + a.getProbCritico());
	}

	private static void imprimirEscudo(Escudos e) {
		System.out.println(
				"- " + e.getNombre() + " | NivelReq: " + e.getNivelRequerido() + " | Durabilidad: " + e.getDurabilidad()
						+ " | Peso: " + e.getPeso() + " | Puntos Resistencia: " + e.getPuntosResistencia());
	}

}
