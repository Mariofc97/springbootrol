package es.cursojava.springbootrol.entities.episodios;

import java.util.logging.Level;
import java.util.logging.Logger;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.utilidades.Utils;

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

	public static void episodio2(Personaje personaje, AccionesEpisodio acciones) {
		// Comprobación inicial: si no nos pasan un personaje, salimos con un mensaje de
		// error

		if (personaje == null) {
			LOGGER.warning("Se llamó a episodio2 con Personaje null");
			System.out.println("Error: personaje no proporcionado.");
			return;
		}

        if (personaje.getEquipo() == null) {
            try {
                personaje.setEquipo(new java.util.ArrayList<>());
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "No se pudo inicializar la lista de equipo", e);
            }
        }

		// Asegurarnos de que la lista de criaturas exista para evitar
        if (personaje.getCriaturas() == null) {
            try {
                personaje.setCriaturas(new java.util.ArrayList<>());
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "No se pudo inicializar la lista de criaturas", e);
            }
        }

		boolean episodio2key1 = false;
		boolean episodio2key2 = false;
		boolean episodio2key3 = false;
		
		boolean salida = false;
        int errorCount = 0;
        final int MAX_ERRORS = 3;

		acciones.add("EPISODIO 2: En el exterior...");
		acciones.add("Sales al bosque y todo parece comida…");
		acciones.add("Avanzas hacia un bosque con apetitosas y llamativas bayas. Tú decides si te las comes…");
		
		do {
			int opcion = (int) (Math.random() * 7) + 1; // genera entre 1 y 7

			switch (opcion) {

            case 1: {
                // buscar bayas (service + recarga)
                personaje = Utils.buscarBaya(personaje);
                episodio2key1 = true;
            }
            break;

            case 2: {
                try {
                    acciones.add("Intentando cazar...");
                    Utils.cazar(personaje, acciones); 

                    if (Utils.fueUltimaCazaExitosa()) {
                        episodio2key2 = true;
                        acciones.add("Caza realizada con éxito.");
                        acciones.add("El personaje " + personaje.getNombre() + " ha cazado con éxito.");
                    } else {
                        acciones.add("La caza no tuvo éxito.");
                    }

                } catch (Exception e) {
                    LOGGER.log(Level.SEVERE, "Error al cazar", e);
                    acciones.add("No se pudo cazar.");
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
				acciones.add("Te escondes entre los arbustos, intentando calmar tu respiración agitada y el latido acelerado de tu corazón. Mientras esperas, escuchas los sonidos del bosque que poco a poco vuelven a la normalidad. Después de unos minutos, te sientes lo suficientemente tranquilo como para salir de tu escondite.");

				episodio2key3 = true;

			}
				break;
			

            case 5: {
                try {
                	String msg = Utils.buscarObjeto(personaje);
                	acciones.add("Buscaste materiales en la cueva. " + msg);
                } catch (Exception e) {
                    acciones.add("Error al buscar materiales.");
                }
            }
            break;

            case 6: {
                if (episodio2key1 && episodio2key2 && episodio2key3) {
                    salida = true;
                    acciones.add("Ya puedes ir al bosque oscuro.");
                } else {
                    acciones.add("Aún no has hecho todo lo necesario para avanzar.");
                }
            }
            break;

            case 7: {
                try {
                    Utils.recuperarVida(personaje);
                    acciones.add("Dormiste profundamente y recuperaste toda la vida.");
                } catch (Exception e) {
                	acciones.add("");
                }
            }
            break;

            default:
                System.out.println("Opción no válida");
        }


		} while (!salida);

	}
}
