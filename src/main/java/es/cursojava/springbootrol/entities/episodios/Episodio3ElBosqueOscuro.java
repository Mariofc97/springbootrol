package es.cursojava.springbootrol.entities.episodios;

import java.util.logging.Level;
import java.util.logging.Logger;

import es.cursojava.springbootrol.entities.Personaje;
import es.cursojava.springbootrol.entities.criatura.Jabali;
import es.cursojava.springbootrol.entities.criatura.Lobo;
import es.cursojava.springbootrol.entities.equipo.Equipamiento;
import es.cursojava.springbootrol.entities.equipo.armas.Trampa;
import es.cursojava.springbootrol.entities.equipo.objetos.Baya;
import es.cursojava.springbootrol.entities.equipo.objetos.CarneSeca;
import es.cursojava.springbootrol.exceptions.ReglaJuegoException;
import es.cursojava.springbootrol.utilidades.Utils;

public class Episodio3ElBosqueOscuro {

	private static final Logger LOGGER = Logger.getLogger(Episodio3ElBosqueOscuro.class.getName());
	static {
		LOGGER.setUseParentHandlers(false);
	}

	public static void episodio3ElBosqueOscuro(Personaje personaje, AccionesEpisodio acciones) {

		if (personaje == null) {
			LOGGER.warning("Se llamó a episodio3ElBosqueOscuro con Personaje null");
			acciones.add("Error: personaje no proporcionado.");
			return;
		}

		if (personaje.getEquipo() == null)
			personaje.setEquipo(new java.util.ArrayList<>());
		if (personaje.getCriaturas() == null)
			personaje.setCriaturas(new java.util.ArrayList<>());

		boolean salida = false;
		boolean bosqueOscurokey1 = false;
		boolean bosqueOscurokey2 = false;
		boolean bosqueOscurokey3 = false;
		boolean controladorAtaqueLobo = false;
		boolean controladorJabali = false;

		acciones.add("");
		acciones.add("\"EPISODIO 3: El Bosque Oscuro\"");
		acciones.add("Te adentras en el Bosque Oscuro, un lugar lleno de misterios y peligros.");
		acciones.add("A medida que avanzas, sientes que los árboles susurran a tu alrededor.");
		acciones.add("Algo acecha...");
		acciones.add("");

//        EquipamientoService equipService = new EquipamientoServiceImpl();
//        CriaturaService criaturaService = new CriaturaServiceImpl();

		do {

//            acciones.add("\n1. Buscar bayas \n2. Cazar  \n3. Usar trampa \n4. Inventario y estado \n5. Buscar materiales \n6. Ir al río \n7. Descansar \n8. Invocar lobo y jabalí.");
//            acciones.add("Di la opción del menú");

			int opcion = (int) (Math.random() * 8) + 1;

			switch (opcion) {

			case 1: {
				personaje = Utils.buscarBaya(personaje);

				if (!controladorJabali) {
					acciones.add(
							"Ummm que ricas las bayas... escuchas un ruido... de repente un jabalí salvaje aparece buscando comida y te ataca.");

					Jabali jabali = new Jabali();
					int expAntes = personaje.getExperiencia();

					boolean ganado = Utils.combate(personaje, jabali);

					if (ganado && personaje.getExperiencia() > expAntes) {
						acciones.add("Has sobrevivido al ataque del jabalí y conseguido bayas.");
						controladorJabali = true;

						try {
							personaje.addEquipamiento(new Baya());
//                            personaje = Utils.recargarPersonaje(personaje.getId());
						} catch (ReglaJuegoException e) {
							acciones.add("No puedes añadir bayas: " + e.getMessage());
						}

						bosqueOscurokey1 = true;
					}

				} else {
					acciones.add("Bien, has encontrado bayas!!!!");
					try {
						personaje.addEquipamiento(new Baya());
//                        personaje = Utils.recargarPersonaje(personaje.getId());
					} catch (ReglaJuegoException e) {
						acciones.add("No puedes añadir bayas: " + e.getMessage());
					}
				}
			}
				break;

			case 2: {
				try {
					int expAntes = personaje.getExperiencia();
					acciones.add("Intentando cazar...");
					personaje = Utils.cazar(personaje);

					if (personaje.getExperiencia() > expAntes) {
						acciones.add("Caza realizada con éxito.");
						LOGGER.info("El personaje " + personaje.getNombre() + " ha cazado con éxito.");
					}
				} catch (Exception e) {
					LOGGER.log(Level.SEVERE, "Error al cazar", e);
					acciones.add("No se pudo cazar.");
				}
			}
				break;

			case 3: {
				int contadorTrampas = 0;
				for (Equipamiento eq : personaje.getEquipo()) {
					if (eq instanceof Trampa)
						contadorTrampas++;
				}

				if (contadorTrampas == 0) {
					acciones.add("No tienes trampas en tu inventario.");
					break;
				}

				if (!controladorAtaqueLobo) {
					acciones.add(
							"Bien, has atrapado un conejo!!!! te acercas despacio pero... sientes como algo te acecha... te ataca un lobo que también quiere el conejo.");

					Lobo lobo = new Lobo();
					int expAntes = personaje.getExperiencia();

					boolean ganado = Utils.combate(personaje, lobo);

					if (ganado && personaje.getExperiencia() > expAntes) {
						acciones.add("Has sobrevivido al ataque del lobo y conseguido el conejo.");

						controladorAtaqueLobo = true;

						try {
							equipService.añadirAlInventario(personaje.getId(), new CarneSeca());
							personaje = Utils.recargarPersonaje(personaje.getId());
						} catch (ReglaJuegoException e) {
							acciones.add("No puedes añadir carne seca: " + e.getMessage());
						}

						bosqueOscurokey1 = true;
					}

				} else {
					acciones.add("Bien has atrapado un conejo!!!!");
					try {
						equipService.añadirAlInventario(personaje.getId(), new CarneSeca());
						personaje = Utils.recargarPersonaje(personaje.getId());
					} catch (ReglaJuegoException e) {
						acciones.add("No puedes añadir carne seca: " + e.getMessage());
					}
				}
			}
				break;

			case 4: {
				try {
					Utils.menuInventario(personaje);
					LOGGER.info("Mostrando inventario de: " + personaje.getNombre());
					personaje = Utils.recargarPersonaje(personaje.getId());
				} catch (Exception e) {
					LOGGER.log(Level.SEVERE, "Error al mostrar el inventario", e);
					acciones.add("No se pudo mostrar el inventario.");
				}
			}
				break;

			case 5: {
				try {
					personaje = Utils.buscarObjeto(personaje);
					LOGGER.info("El personaje " + personaje.getNombre() + " ha buscado un objeto.");
				} catch (Exception e) {
					LOGGER.log(Level.SEVERE, "Error al buscar objeto", e);
					acciones.add("No se pudo buscar el objeto.");
				}
			}
				break;

			case 6: {
				if (bosqueOscurokey1 && bosqueOscurokey2 && bosqueOscurokey3) {
					salida = true;
					acciones.add("Ya puedes ir al río.");
				} else {
					acciones.add("Aún no cumples los requisitos para avanzar.");
				}
			}
				break;

			case 7: {
				Utils.recuperarVida(personaje);
				acciones.add("Has descansado y recuperado toda la vida.");
				LOGGER.info("Descanso. Personaje: " + personaje.getNombre());
				bosqueOscurokey2 = true;
			}
				break;

			case 8: {
				if (controladorAtaqueLobo && controladorJabali) {
					acciones.add("Ya puedes invocar a tu lobo o jabalí compañero.");
					bosqueOscurokey3 = true;
					Utils.invocarLoboJabali(personaje);
					personaje = Utils.recargarPersonaje(personaje.getId());
				} else {
					acciones.add("Aún no has derrotado a un lobo y un jabalí, no puedes invocarlos.");
				}
			}
				break;

			default:
				acciones.add("Opción no válida");
			}

		} while (!salida);
	}
}
