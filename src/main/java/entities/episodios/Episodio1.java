package entities.episodios;

import java.util.InputMismatchException;
import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.Personaje;
import entities.equipo.objetos.HojaParaLimpiar;
import service.PersonajeService;
import utilidades.Utils;

/**
 * Clase que implementa el episodio 1 del juego.
 *
 * Contiene un menú interactivo que permite al personaje realizar acciones
 * (obtener objetos, aprender invocaciones, recibir desgracias o dormir).
 *
 * El logger se configura en un bloque estático para escribir en
 * "episodio1.log".
 */
@Service
public class Episodio1 {
	// Logger específico para esta clase
	private static final Logger LOGGER = Logger.getLogger(Episodio1.class.getName());

	@Autowired 
	private PersonajeService personajeService;

	static {
		LOGGER.setUseParentHandlers(false); // evita que el logger escriba en consola
	}
	// crear un menu:necesito un switch.

	/**
	 * Método principal del episodio 1 que ejecuta un menú interactivo para el
	 * personaje. QUITO STATIC DEL METODO DA ERROR CON EQUIPSERVICE
	 *
	 * @param personaje instancia del personaje que participa en el episodio. Puede
	 *                  ser null; en ese caso el método registrará y devolverá sin
	 *                  ejecutar.
	 */
	public void episodio1(Personaje personaje) { // personaje es el nombre del personaje.
		// Comprobación inicial: si no nos pasan un personaje, salimos con un mensaje de
		// error
		// EquipamientoService equipService = new EquipamientoServiceImpl();
		// CriaturaService criaturaService = new CriaturaServiceImpl();

		if (personaje == null) {
			LOGGER.warning("Se llamó a episodio1 con Personaje null");
			System.out.println("Error: personaje no proporcionado.");
			return;
		}

		// Asegurarnos de que la lista de equipo exista para evitar NullPointerException
		if (personaje.getEquipo() == null) {
			try {
				// Inicializamos una lista vacía si no existe
				java.util.List<entities.equipo.Equipamiento> equipoList = new java.util.ArrayList<>();
				personaje.setEquipo(equipoList);
				//LOGGER.info("Se inicializó la lista de equipo para el personaje: " + personaje.getNombre());
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
				//LOGGER.info("Se inicializó la lista de criaturas para el personaje: " + personaje.getNombre());
			} catch (Exception e) {
				LOGGER.log(Level.WARNING, "No se pudo inicializar la lista de criaturas", e);
			}
		}
		// llamadas!!!!!!!!

		// Flags que representan si el personaje ya ha realizado ciertas acciones
		// key1: consiguió HojaParaLimpiar
		// key2: aprendió a invocar criaturas
		// key3: sufrió la desgracia o durmió (ambas ponen a 1 la vida o la restauran)

		boolean key1 = false;
		boolean key2 = false;
		boolean key3 = false;
		
		boolean salida = false; // control del bucle principal
		// contador de fallos consecutivos para evitar bucle infinito
		int errorCount = 0;
		final int MAX_ERRORS = 3;

		LOGGER.info(() -> ("COMIENZA EL JUEGO UGHA BUGHA!"));
		System.out.println();
		System.out.println("EPISODIO 1: La Cueva");
		System.out.println("Despiertas en una cueva. No recuerdas tu nombre… pero tu olor sí");
		System.out.println("Un eco te susurra: 'Para sobrevivir necesitas: llorar, pensar y NO morir.");
		System.out.println();

		do {
			try {
//				LOGGER.info("Mostrando menú para personaje: " + personaje.getNombre());

				System.out.println(
						"\n1. Llorar de forma desconsolado \n2. Pensar en un compañero de vieje \n3. Salir de la cueva \n4. Dormir y recuperar vida \n5. Buscar materiales");
				int opcion;
				try {
					// Pedimos un número al usuario mediante utilidades comunes
					// opcion = Utils.pideDatoNumerico("Que quieres hacer?");

					opcion = (int) (Math.random() * 5) + 1; // genera entre 1 y 5
					// juega solo de froma aleatoria.
//					LOGGER.info("Opción elegida: " + opcion);
				} catch (InputMismatchException | NumberFormatException ex) {
					// Si la entrada no es un número, informamos y volvemos a mostrar el menú
					LOGGER.log(Level.WARNING, "Entrada numérica no válida", ex);
					System.out.println("Entrada no válida. Introduce un número.");
					continue; // volver a mostrar menú
				} catch (Exception ex) {
					// Cualquier otro error al leer la opción lo registramos y reintentamos
//					LOGGER.log(Level.SEVERE, "Error obteniendo la opción del usuario", ex);
					System.out.println("Se produjo un error al leer la opción. Inténtalo de nuevo.");
					continue;
				}

				switch (opcion) {

				case 1: {
					// quito control de exceptions porque no hay nada que pueda fallar realmente.
					int cantidad = Utils.contarHojas(personaje);
					if (cantidad >= 5) {
						System.out.println("Te dan un torta... LLORON DEJA DE LLORAR!!!!!");
						break;
					}
//TODO: TENEMOS QUE VOLVER AL METODO ANTIGURO Y HACERLO PERSISTENTE AL FINAL DEL EPISODIO.
					//equipService.añadirAlInventario(personaje.getId(), new HojaParaLimpiar());
					//personaje = Utils.recargarPersonaje(personaje.getId());
					personaje.getEquipo().add(new HojaParaLimpiar());
					System.out.println("Has obtenido una Hoja de Ortiga.");
					key1 = true;
				}
					break;

				case 2: {
					try {
						int antes = personaje.getCriaturas().size();
						Utils.invocacionCompañeroCriatura(personaje);
						//personaje = Utils.recargarPersonaje(personaje.getId());
						int despues = personaje.getCriaturas().size();

						if (despues > antes) {
							key2 = true;
							System.out.println("Has invocado una criatura.");
						} else {
							System.out.println("No has conseguido invocar ninguna criatura. Inténtalo de nuevo.");
						}

					} catch (Exception e) {
						//LOGGER.log(Level.SEVERE, "Error en invocacionCompañeroCriatura", e);
						System.out.println("Se produjo un error al invocar la criatura.");
					}
				}
					break;

				case 3: {
				    try {
				        int cantidad = personaje.getPuntosVida();
				        if (cantidad <= 1) {
				            System.out.println(
				                "No se puede salir de la cueva con 1 de vida, duerme un poco y prueba de nuevo suicida!!!!");
				            break;
				        }

				        // Si ya cumplió las 3 claves → puede salir
				        if (key1 && key2 && key3) {

				            // Persistimos el personaje ANTES de salir del episodio
				            personajeService.actualizar(personaje);

				            String msg = " Saliendo del fuera de la cueva...";
				            System.out.println(msg);
				            LOGGER.info(msg + " Personaje: " + personaje.getNombre());
				            salida = true;
				            break;
				        }

				        // Si aún no puede salir → desgracia
				        personaje.setPuntosVida(1);
				        System.out.println(Utils.desgraciaAleatorio() + " Tu vida ahora es: "
				                + personaje.getPuntosVida() + " y vuelves a la cueva, llorando...");
				        key3 = true;

				    } catch (Exception e) {
				        System.out.println("No se pudo realizar la acción de la opción 3.");
				    }
				}
				break;


				case 4: {
					// Caso 4: dormir y recuperar toda la vida
					Utils.recuperarVida(personaje);
					String msg = "Has dormido y recuperado toda la vida.";
					System.out.println(msg);
					key3 = true; // dormir también cuenta como requisito para poder salir
					//LOGGER.info(msg + " Personaje: " + personaje.getNombre());

				}
					break;

					
				case 5: {
					// Caso 6: buscar objeto
					// hacer el control de exdesde aqui.
					try {
						personaje = Utils.buscarObjeto(personaje);
					//	LOGGER.info("El personaje " + personaje.getNombre() + " ha buscado un objeto.");
					} catch (Exception e) {
					//	LOGGER.log(Level.SEVERE, "Error al buscar objeto", e);
						System.out.println("No se pudo buscar el objeto.");
					}

				}
					break;

				default:
					// Opción inválida: avisar al usuario y registrar
					System.out.println("Opción no válida");
					LOGGER.warning("Opción no válida elegida: " + opcion + " Personaje: " + personaje.getNombre());
				}

				// Actualizamos la condición de salida: si se han cumplido las 3 claves salimos

				// resetear contador de errores tras operación exitosa
				errorCount = 0;

			} catch (Throwable t) {
				// Capturamos Throwable para evitar salidas inesperadas y lo registramos
				//LOGGER.log(Level.SEVERE, "Excepción inesperada en episodio1", t);
				System.out.println("Se ha producido un error inesperado. Reintentando...");
				// Mostrar traza en consola para diagnóstico (ayuda a ver el stacktrace real)
				t.printStackTrace();
				errorCount++;
				if (errorCount >= MAX_ERRORS) {
					String msg = "Se han producido varios errores consecutivos. Abortando episodio para evitar bucle infinito.";
					System.out.println(msg);
					//LOGGER.severe(msg);
					salida = true; // forzamos salida del bucle
				}
			}
		} while (!salida);
		// Guardar personaje al final del episodio por seguridad 
		personajeService.actualizar(personaje);

	}

}
