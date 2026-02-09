package es.cursojava.springbootrol.entities.episodios;

import java.util.logging.Level;
import java.util.logging.Logger;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.criatura.PezPrehistoricoGigante;
import es.cursojava.springbootrol.entities.equipo.armas.CanaPescar;
import es.cursojava.springbootrol.entities.equipo.objetos.CarneSeca;
import es.cursojava.springbootrol.exceptions.ReglaJuegoException;
import es.cursojava.springbootrol.utilidades.JuegoActions;

public class Episodio4Rio {

	static int contadorEpisodio4 = 0;
	private static final Logger LOGGER = Logger.getLogger(Episodio4Rio.class.getName());

	static {
		LOGGER.setUseParentHandlers(false);
	}

	public static void episodio4Rio(Personaje personaje, AccionesEpisodio acciones) {

		if (personaje == null) {
			LOGGER.warning("Se llamó a episodio4Rio con Personaje null");
			acciones.add("Error: personaje no proporcionado.");
			return;
		}

		if (personaje.getEquipo() == null) {
			try {
				personaje.setEquipo(new java.util.ArrayList<>());
				LOGGER.info("Se inicializó la lista de equipo para el personaje: " + personaje.getNombre());
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "No se pudo inicializar la lista de equipo", e);
			}
		}

		if (personaje.getCriaturas() == null) {
			try {
				personaje.setCriaturas(new java.util.ArrayList<>());
				LOGGER.info("Se inicializó la lista de criaturas para el personaje: " + personaje.getNombre());
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "No se pudo inicializar la lista de criaturas", e);
			}
		}

		boolean pezPrehistoricoGigante = false;
		boolean salida = false;
		boolean Riokey1 = false;
		boolean Riokey2 = false;
		boolean Riokey3 = false;

//        EquipamientoService equipService = new EquipamientoServiceImpl();
//        CriaturaService criaturaService = new CriaturaServiceImpl();

		do {

			acciones.add("1. Buscar bayas");
			acciones.add("2. Pescar");
			acciones.add("3. Bañarte");
			acciones.add("4. CREAR CAÑA DE PESCAR");
			acciones.add("5. Buscar materiales");
			acciones.add("6. Ir al río");
			acciones.add("7. Descansar");
			acciones.add("8. Invocar PezPrehistoricoGigante");
			acciones.add("Elige una opción...");

			int opcion = (int) (Math.random() * 8) + 1;

			switch (opcion) {

			case 1: {
				JuegoActions.buscarBaya(personaje);
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
					acciones.add("Intentas pescar, pero sin una caña es imposible.");
					acciones.add("Necesitas fabricar o encontrar una caña de pescar.");
					LOGGER.info("El personaje " + personaje.getNombre() + " intentó pescar sin caña.");
					break;
				}

				acciones.add("Lanzas la caña al río y esperas pacientemente...");

				int tirada = JuegoActions.dadoDiez();

				if (tirada <= 2) {
					acciones.add("Nada pica esta vez. El río sigue en calma.");
				} else {
					acciones.add("¡La caña se tensa con fuerza! Has pescado un Siluro.");

					personaje.addEquipamiento(new CarneSeca());

					// personaje = Utils.recargarPersonaje(personaje.getId());
					Riokey1 = true;

					LOGGER.info("El personaje " + personaje.getNombre() + " pescó un Siluro.");
				}
			}
				break;

			case 3: {
				acciones.add("Te bañas en el río, sintiendo el agua fresca y revitalizante...");
				acciones.add("Notas que algo se mueve en el agua... ¡Te ataca un PezPrehistoricoGigante!");

				if (!pezPrehistoricoGigante) {

					PezPrehistoricoGigante pez = new PezPrehistoricoGigante();
					boolean resultado = JuegoActions.combate(personaje, pez);

					if (resultado) {
						Riokey2 = true;
						pezPrehistoricoGigante = true;
					}
				}
			}
				break;

			case 4: {
				// CREAR CAÑA DE PESCAR
			}
				break;

			case 5: {
				try {
					JuegoActions.buscarObjeto(personaje);
					LOGGER.info("El personaje " + personaje.getNombre() + " ha buscado un objeto.");
				} catch (Exception e) {
					LOGGER.log(Level.SEVERE, "Error al buscar objeto", e);
					acciones.add("No se pudo buscar el objeto.");
				}
			}
				break;

			case 6: {
				if (Riokey1 && Riokey2 && Riokey3) {
					salida = true;
					acciones.add(
							"Has superado todos los obstáculos del río. Es hora de volver al Clan y enfrentarte al Jefe.");
				} else {
					acciones.add("Aún no cumples los requisitos para avanzar.");
				}
			}
				break;

			case 7: {
				JuegoActions.recuperarVida(personaje);
				acciones.add("Has dormido y recuperado toda la vida.");
				LOGGER.info("Descanso. Personaje: " + personaje.getNombre());
			}
				break;

			case 8: {

				if (pezPrehistoricoGigante) {

					if (JuegoActions.dadoDiez() < 3) {
						acciones.add(
								"Intentas invocar a tu PezPrehistoricoGigante, pero estornudas y lo enfureces. ¡Te ataca!");
						JuegoActions.combate(personaje, new PezPrehistoricoGigante());
					} else {
						Riokey3 = true;

						acciones.add(
								"Invocas a tu PezPrehistoricoGigante. Emerge del agua con un gran salto y se convierte en tu compañero.");
						personaje.getCriaturas().add(new PezPrehistoricoGigante());
					}

				} else {
					acciones.add("Aún no has derrotado a un PezPrehistoricoGigante. No puedes invocarlo.");
				}
			}
				break;

			default:
				acciones.add("Opción no válida");
			}

		} while (!salida);
	}
}
