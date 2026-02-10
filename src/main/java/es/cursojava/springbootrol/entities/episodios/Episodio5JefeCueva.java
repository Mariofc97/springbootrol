package es.cursojava.springbootrol.entities.episodios;

import java.util.logging.Logger;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.criatura.JefeDelClan;
import es.cursojava.springbootrol.entities.criatura.PezPrehistoricoGigante;
import es.cursojava.springbootrol.utilidades.JuegoActions;

public class Episodio5JefeCueva {

	private static final Logger LOGGER = Logger.getLogger(Episodio5JefeCueva.class.getName());
	static {
		LOGGER.setUseParentHandlers(false);
	}

	public void episodio5JefeClan(Personaje personaje, AccionesEpisodio acciones) {

		if (personaje == null) {
			LOGGER.warning("Se llamó a episodio5 con Personaje null");
			acciones.add("Error: personaje no proporcionado.");
			return;
		}

		if (personaje.getEquipo() == null)
			personaje.setEquipo(new java.util.ArrayList<>());

		if (personaje.getCriaturas() == null)
			personaje.setCriaturas(new java.util.ArrayList<>());

		acciones.add("\"EPISODIO 5: El Jefe del Clan\"");
		acciones.add("Tras superar todas las pruebas, regresas al Clan para enfrentarte al Jefe.");
		acciones.add("El ambiente es tenso. Todos te observan. Ha llegado el momento de demostrar quién eres.");

		boolean jefekey2 = false;
		boolean salida = false;
		int contadorCorrer = 0;
		int contadorGolpes = 0;
		int vidaInicial = personaje.getPuntosVida();

		do {

			int opcion = (int) (Math.random() * 6) + 1;

			switch (opcion) {

			case 1: {
				if (contadorCorrer > 5) {
					acciones.add("Corres demasiado. El clan te llama cobarde. Pierdes todos los aumentos de vida.");
					personaje.setPuntosVida(vidaInicial);
				} else {
					acciones.add("Huyes presa del miedo, pero algo cambia en ti. Regresas con +50 de vida.");
					contadorCorrer++;
					personaje.setPuntosVida(personaje.getPuntosVida() + 50);
				}
				break;
			}

			case 2: {
				if (contadorGolpes > 5) {
					acciones.add("Te golpeas demasiado fuerte. Te rompes dos costillas. Vida reducida a 1.");
					personaje.setPuntosVida(1);
				} else {
					acciones.add("Golpeas tu pecho y ruges. El clan te observa. +10 ataque.");
					contadorGolpes++;
					personaje.setPuntosAtaque(personaje.getPuntosAtaque() + 10);
				}
				break;
			}

			case 3: {
				JuegoActions.recuperarVida(personaje, acciones);
				acciones.add("Descansas y recuperas toda la vida. Los aumentos temporales desaparecen.");
				break;
			}

			case 4: {
				if (JuegoActions.dadoDiez() <= 5) {
					JuegoActions.invocarLoboJabali(personaje, acciones);
				} else {
					acciones.add("Invocas a tu PezPrehistoricoGigante. Emerge del agua y se une a ti.");
					personaje.getCriaturas().add(new PezPrehistoricoGigante());
				}
				break;
			}

			case 5: {
				try {
					JuegoActions.buscarObjeto(personaje, acciones);
				} catch (Exception e) {
					acciones.add("No se pudo buscar el objeto.");
				}
				break;
			}

			case 6: {
				acciones.add("Te preparas para desafiar al Jefe del Clan...");
				JefeDelClan jefe = new JefeDelClan();
				boolean resultadoFinal = JuegoActions.combateAuto(personaje, jefe, acciones);
				jefekey2 = true;

				if (resultadoFinal) {
					acciones.add("La locura te domina. Matas al Jefe del Clan... y luego a todos los presentes.\n"
							+ "Cuando recuperas la cordura, estás solo, cubierto de sangre.\n"
							+ "Has perdido todo. Eres desterrado para siempre.");
				} else {
					acciones.add("Caes derrotado, pero el Jefe te ayuda a levantarte.\n"
							+ "\"Has cambiado. Ahora tienes honor\", dice.\n"
							+ "El Clan te acepta de nuevo. Recuperas tu familia y tu lugar.");
				}

				break;
			}

			default:
				acciones.add("Opción no válida.");
			}

			salida = jefekey2;

		} while (!salida);

		acciones.add("Fin del episodio 5.");
	}
}
