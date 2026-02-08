package entities.episodios;

import java.util.logging.Level;
import java.util.logging.Logger;

import entities.Personaje;
import utilidades.Utils;

public class Episodio2 {
	// FIXME: hay que declarar las keys como static para que se mantengan entre
	// TODO: FALTA REPASAR CONTADOR DE EPISODIO2 PARA QUE FUNCIONE BIEN ENTRE
	// llamadas!!!!!!!!
	static int contadorEpisodio2 = 0;
	// Logger específico para esta clase
	private static final Logger LOGGER = Logger.getLogger(Episodio2.class.getName());

	static {
		LOGGER.setUseParentHandlers(false); // evita que el logger escriba en consola
	}

	public static void episodio2(Personaje personaje) {
		// Comprobación inicial: si no nos pasan un personaje, salimos con un mensaje de
		// error

		if (personaje == null) {
			LOGGER.warning("Se llamó a episodio2 con Personaje null");
			System.out.println("Error: personaje no proporcionado.");
			return;
		}

		// Asegurarnos de que la lista de equipo exista para evitar NullPointerException
		if (personaje.getEquipo() == null) {
			try {
				// Inicializamos una lista vacía si no existe
				java.util.List<entities.equipo.Equipamiento> equipoList = new java.util.ArrayList<>();
				personaje.setEquipo(equipoList);
				LOGGER.info("Se inicializó la lista de equipo para el personaje: " + personaje.getNombre());
			} catch (Exception e) {
				// Si falla la inicialización la registramos pero no abortamos el episodio
				LOGGER.log(Level.WARNING, "No se pudo inicializar la lista de equipo", e);
			}
		}

		// Asegurarnos de que la lista de criaturas exista para evitar
		// NullPointerException
		if (personaje.getCriaturas() == null) {
			try {
				java.util.List<entities.criatura.Criatura> criaturasList = new java.util.ArrayList<>();
				personaje.setCriaturas(criaturasList);
				LOGGER.info("Se inicializó la lista de criaturas para el personaje: " + personaje.getNombre());
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "No se pudo inicializar la lista de criaturas", e);
			}
		}

		boolean episodio2key1 = false;
		boolean episodio2key2 = false;
		boolean episodio2key3 = false;
		
		boolean salida = false;

		System.out.println();
		System.out.println("EPISODIO 2: En el exterior...");
		System.out.println("Sales al bosque y todo parece comida…");
		System.out.println("Avanzas hacia un bosque con apetitosas y llamativas bayas. Tú decides si te las comes…");
		System.out.println();
		
		do {
			int opcion;

			System.out.println(
					"\n1. Buscar bayas \n2. Cazar \n3. Crear arma \n4. Esconderse del miedo \n.Inventario y estado \n5.Buscar materiales \n6.Ir al bosque oscuro \n7.Descansar");
			opcion = (int) (Math.random() * 7) + 1; // genera entre 1 y 7

			switch (opcion) {

            case 1: {
                // buscar bayas (service + recarga)
                personaje = Utils.buscarBaya(personaje);
                episodio2key1 = true;
            }
            break;

            case 2: {
                try {
                    System.out.println("Intentando cazar...");
                    personaje = Utils.cazar(personaje);

                    if (Utils.fueUltimaCazaExitosa()) {
                        episodio2key2 = true;
                        System.out.println("Caza realizada con éxito.");
                        LOGGER.info("El personaje " + personaje.getNombre() + " ha cazado con éxito.");
                    }

                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error al cazar", e);
                    System.out.println("No se pudo cazar.");
                }
            }
            break;

            case 3: {
                // fabricar (service)
                try {
                    Utils.menuFabricar(personaje);
                    // el inventario actualizado se ve al instante en el episodio:
                    personaje = Utils.recargarPersonaje(personaje.getId());
                    episodio2key3 = true;
                } catch (Exception e) {
                    System.out.println("No puedes fabricar: " + e.getMessage());
                }
            }
            break;

			case 4: {
				// esconderse del miedo
				System.out.println(
						"Te escondes entre los arbustos, intentando calmar tu respiración agitada y el latido acelerado de tu corazón. Mientras esperas, escuchas los sonidos del bosque que poco a poco vuelven a la normalidad. Después de unos minutos, te sientes lo suficientemente tranquilo como para salir de tu escondite.");

				episodio2key3 = true;

			}
				break;
			

            case 5: {
                try {
                    personaje = Utils.buscarObjeto(personaje); // ya devuelve recargado
                    LOGGER.info("El personaje " + personaje.getNombre() + " ha buscado un objeto.");
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error al buscar objeto", e);
                    System.out.println("No se pudo buscar el objeto.");
                }
            }
            break;

            case 6: {
                if (episodio2key1 && episodio2key2 && episodio2key3) {
                    salida = true;
                    System.out.println("Ya puedes ir al bosque oscuro.");
                } else {
                    System.out.println("Aún no has hecho todo lo necesario para avanzar.");
                }
            }
            break;

            case 7: {
                try {
                    personaje.setPuntosVida(personaje.getPuntosVidaMax());
                    String msg = "Has descansado y recuperado toda la vida.";
                    System.out.println(msg);
                    LOGGER.info(msg + " Personaje: " + personaje.getNombre());
                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error al descansar", e);
                    System.out.println("No se pudo descansar correctamente.");
                }
            }
            break;

            default:
                System.out.println("Opción no válida");
        }


		} while (!salida);

	}
}
