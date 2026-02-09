package es.cursojava.springbootrol.entities.episodios;

import java.util.logging.Level;
import java.util.logging.Logger;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.criatura.PezPrehistoricoGigante;
import es.cursojava.springbootrol.entities.equipo.armas.CanaPescar;
import es.cursojava.springbootrol.entities.equipo.objetos.CarneSeca;
import es.cursojava.springbootrol.exceptions.ReglaJuegoException;
import es.cursojava.springbootrol.service.CriaturaService;
import es.cursojava.springbootrol.service.EquipamientoService;
import es.cursojava.springbootrol.service.impl.CriaturaServiceImpl;
import es.cursojava.springbootrol.service.impl.EquipamientoServiceImpl;
import es.cursojava.springbootrol.utilidades.Utils;

public class Episodio4Rio {
	static int contadorEpisodio4 = 0;
	private static final Logger LOGGER = Logger.getLogger(Episodio4Rio.class.getName());
	static {
		LOGGER.setUseParentHandlers(false); // evita que el logger escriba en consola
	}

	// comienza las validaciones de personaje y listas
	public static void episodio4Rio(Personaje personaje) {
		EquipamientoService equipService = new EquipamientoServiceImpl();
		CriaturaService criaturaService = new CriaturaServiceImpl();
		if (personaje == null) {
			LOGGER.warning("Se llamó a episodio3ElBosqueOscuro con Personaje null");
			System.out.println("Error: personaje no proporcionado.");
			return;
		}

		// Asegurarnos de que la lista de equipo exista para evitar NullPointerException
		if (personaje.getEquipo() == null) {
			try {
				// Inicializamos una lista vacía si no existe

				java.util.List<es.cursojava.springbootrol.entities.equipo.Equipamiento> equipoList = new java.util.ArrayList<>();
				personaje.setEquipo(equipoList);
				LOGGER.info("Se inicializó la lista de equipo para el personaje: " + personaje.getNombre());
			} catch (Exception e) {
				// Si falla la inicialización la registramos pero no abortamos el episodio
				LOGGER.log(java.util.logging.Level.WARNING, "No se pudo inicializar la lista de equipo", e);
			}
		}

		// Asegurarnos de que la lista de criaturas exista para evitar
		// NullPointerException
		if (personaje.getCriaturas() == null) {
			try {
				java.util.List<es.cursojava.springbootrol.entities.criatura.Criatura> criaturasList = new java.util.ArrayList<>();
				personaje.setCriaturas(criaturasList);
				LOGGER.info("Se inicializó la lista de criaturas para el personaje: " + personaje.getNombre());
			} catch (Exception e) {
				LOGGER.log(java.util.logging.Level.WARNING, "No se pudo inicializar la lista de criaturas", e);
			}
		}
		boolean pezPrehistoricoGigante = false;
		boolean salida = false;
		boolean Riokey1 = false;
		boolean Riokey2 = false;
		boolean Riokey3 = false;
		if (contadorEpisodio4 == 0) {
			System.out.println(
					"Tras atravesar el bosque oscuro, llegas a un río caudaloso que bloquea tu camino. El agua corre lentamente, limpia y clara ... enseguida se te ocurren muchas cosas que podrias haces.");
			contadorEpisodio4++;
		}
		// comienza la logica del episodio
		do {

			System.out.println(
					"1. Buscar bayas \n2. Pescar \n3. Crear arma \n4. Bañarte \n5.Inventario y estado \n6.Buscar materales. \n7.Desafiar Jefe del Clan. \n8.Descansar. \n9.Invocar PezPrehistoricoGigante");
			System.out.println("di la opcion del menu");
			int opcion = Utils.pideDatoNumerico("Que quieres hacer?");

			switch (opcion) {

			case 1: {
				// buscar bayas
				// TODO: HAY Q AÑADIR QUE AL BUSCAR BAYAS NOS ENCONTRAMOS CON UN JABALI Y PELA
				// BOSH FACIL

				Utils.buscarBaya(personaje);

			}
				break;

			case 2: {

				boolean tieneCana = false;

				for (Object obj : personaje.getEquipo()) {
					if (obj instanceof CanaPescar) {
						tieneCana = true;
						break;
					}
				}

				if (!tieneCana) {
					System.out.println("Intentas pescar, pero sin una caña es imposible.");
					System.out.println("Necesitas fabricar o encontrar una caña de pescar.");
					LOGGER.info("El personaje " + personaje.getNombre() + " intentó pescar sin caña.");
					break;
				}

				// Pesca con caña
				System.out.println("Lanzas la caña al río y esperas pacientemente...");

				int tirada = Utils.dadoDiez();

				if (tirada <= 2) {
					System.out.println("Nada pica esta vez. El río sigue en calma.");
				} else {
					System.out.println("¡La caña se tensa con fuerza! Has pescado un Siluro.");
					// personaje.getEquipo().add(new CarneSeca());

					try {
						equipService.añadirAlInventario(personaje.getId(), new CarneSeca());
					} catch (ReglaJuegoException e) {
						// TODO Auto-generated catch block
						e.printStackTrace();
					}
					personaje = Utils.recargarPersonaje(personaje.getId());
					Riokey1 = true; // ✅ SOLO aquí, cuando pesca de verdad

					LOGGER.info("El personaje " + personaje.getNombre() + " pescó un Siluro.");
				}
			}
				break;

			case 3: {

				// TODO: FALTA AÑADIR TRAMPA PARA PELEA CON JABALI O LOBO
				Utils.menuFabricar(personaje);

			}
				break;

			case 4: {
				// bañarte
				System.out.println(
						"Te bañas en el río, sintiendo el agua fresca y revitalizante que recorre tu cuerpo, observando el paisaje a tu alrededor, notas que algo se mueve en el agua... que es esa mancha enorme??? se dirige hacia ti rapidamente... Te ataca PezPrehistoricoGigante!");
				// añadir combate con pez prehistorico gigante
				if (!pezPrehistoricoGigante) {

					PezPrehistoricoGigante pezPrehistorico = new PezPrehistoricoGigante();
					boolean resulatado = Utils.combate(personaje, pezPrehistorico);
					if (resulatado) {
						Riokey2 = true;
						pezPrehistoricoGigante = true;
					}
				}

			}
				break;
			case 5: {
				// inventario
				try {
					Utils.menuInventario(personaje);
					LOGGER.info("Mostrando inventario de: " + personaje.getNombre());
				} catch (Exception e) {
					LOGGER.log(Level.SEVERE, "Error al mostrar el inventario", e);
					System.out.println("No se pudo mostrar el inventario.");
				}
			}
				break;
			case 6: {
				// buscar materiales
				// Caso 6: buscar objeto
				try {
					Utils.buscarObjeto(personaje);
					LOGGER.info("El personaje " + personaje.getNombre() + " ha buscado un objeto.");
				} catch (Exception e) {
					LOGGER.log(Level.SEVERE, "Error al buscar objeto", e);
					System.out.println("No se pudo buscar el objeto.");
				}
			}
				break;
			case 7: {
				// ir al rio hay q hacer las key
				if (Riokey1 && Riokey2 && Riokey3) {
					salida = true;
					System.out.println(
							"Despues de todo, has superado todos obstaculos, te siente distinto, lo piensa y... has matado un jabali, un lobo, has derrotado a una mostruosa criatura ancestral que vivia en el rio... creo que es hora de volver al Clan y enfrentarse con el Jefe y demostrar que ya no eres el lloron que abandono la cueva...");
				}
				break;

			}
			case 8: {
				// descansar
				Utils.recuperarVida(personaje);
				String msg = "Has dormido y recuperado toda la vida.";
				System.out.println(msg);
				LOGGER.info(msg + " Personaje: " + personaje.getNombre());

			}
				break;
			case 9: {
			
				// invocar pez prehistorico gigante
				if (pezPrehistoricoGigante) {
					if (Utils.dadoDiez() < 3) {
						System.out.println(
								"Estas invocando a tu PezPrehistoricoGigante como compañero, mojado, tiritando y no puedes contener un estornudo, interumpe la invocación de tu PezPrehistoricoGigante y te ataca descontroladamente!");
						Utils.combate(personaje, new PezPrehistoricoGigante());
					} else {
						Riokey3 = true;

						System.out.println(
								"Invocas a tu PezPrehistoricoGigante, que emerge del agua con un gran salto, sacudiendo su enorme cuerpo y creando una ola que te empapa por completo. Ahora está a tu lado, listo para ayudarte en tus aventuras.");
						personaje.getCriaturas().add(new PezPrehistoricoGigante());
					}

				} else {
					System.out.println("Aun no has derrotado a un PezPrehistoricoGigante, no puedes invocarlo.");
				}
			}
				break;

			default:
				System.out.println("Opción no válida");
			}

		} while (!salida);

	}
}
