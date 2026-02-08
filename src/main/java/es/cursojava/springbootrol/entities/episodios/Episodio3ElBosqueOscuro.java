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
import es.cursojava.springbootrol.service.CriaturaService;
import es.cursojava.springbootrol.service.EquipamientoService;
import es.cursojava.springbootrol.service.impl.CriaturaServiceImpl;
import es.cursojava.springbootrol.service.impl.EquipamientoServiceImpl;
import es.cursojava.springbootrol.utilidades.Utils;

//cosas que hacer en el bosque oscuro, como encontrar objetos, criaturas, luchar contra enemigos.

public class Episodio3ElBosqueOscuro {
	EquipamientoService equipService = new EquipamientoServiceImpl();
	CriaturaService criaturaService = new CriaturaServiceImpl();
	
	static int contadorEpisodio3 = 0;
	private static final Logger LOGGER = Logger.getLogger(Episodio3ElBosqueOscuro.class.getName());
	static {
		LOGGER.setUseParentHandlers(false); // evita que el logger escriba en consola
	}

	public static void episodio3ElBosqueOscuro(Personaje personaje) {
	      EquipamientoService equipService = new EquipamientoServiceImpl();

		if (personaje == null) {
			LOGGER.warning("Se llamó a episodio3ElBosqueOscuro con Personaje null");
			System.out.println("Error: personaje no proporcionado.");
			return;
		}

		// Asegurarnos de que la lista de equipo exista para evitar NullPointerException
		// Asegurarnos de que la lista de criaturas exista para evitar NullPointerException
        if (personaje.getEquipo() == null) personaje.setEquipo(new java.util.ArrayList<>());
        if (personaje.getCriaturas() == null) personaje.setCriaturas(new java.util.ArrayList<>());
        
		boolean salida = false;
		boolean bosqueOscurokey1 = false;
		boolean bosqueOscurokey2 = false;
		boolean bosqueOscurokey3 = false;
		boolean controladorAtaqueLobo = false; // false no lo derrotado aun, true derrotado
		boolean controladorJabali = false; // false no lo derrotado aun, true derrotado
		if (contadorEpisodio3 == 0) {
			contadorEpisodio3++;
		}

		System.out.println();
		System.out.println("\"EPISODIO 3: El Bosque Oscuro\"");
		System.out.println("Te adentras en el Bosque Oscuro, un lugar lleno de misterios y peligros.");
		System.out.println("A medida que avanzas, sientes que los árboles susurran a tu alrededor.");
		System.out.println("Algo acecha...");
		System.out.println();

		do {

			System.out.println(
					"\n1. Buscar bayas \n2. Cazar \n3. Crear arma \n4. Usar trampa \n5.Inventario y estado \n6.Buscar materales. \n7.Ir al rio. \n8.Descansar. \n9.Invocar lobo y jabali.");
			System.out.println("di la opcion del menu");
			int opcion = Utils.pideDatoNumerico("Que quieres hacer?");

			switch (opcion) {

			case 1: {
                // Buscar bayas (service + recarga)
                personaje = Utils.buscarBaya(personaje);

                // Evento especial: primer encuentro con jabalí
                if (!controladorJabali) {
                    System.out.println(
                        "Ummm que ricas las bayas... escuchas un ruido... " +
                        "de repente un jabalí salvaje aparece buscando comida y te ataca."
                    );

                    Jabali jabali = new Jabali();
                    int expAntes = personaje.getExperiencia();

                    boolean ganado = Utils.combate(personaje, jabali);

                    // Si quieres seguir usando “exp antes/después”:
                    if (ganado && personaje.getExperiencia() > expAntes) {
                        System.out.println("Has sobrevivido al ataque del jabalí y conseguido bayas.");
                        controladorJabali = true;

                        try {
                            equipService.añadirAlInventario(personaje.getId(), new Baya());
                            personaje = Utils.recargarPersonaje(personaje.getId());
                        } catch (ReglaJuegoException e) {
                            System.out.println("No puedes añadir bayas: " + e.getMessage());
                        }

                        bosqueOscurokey1 = true;
                    }
                } else {
                    // Ya derrotado: solo “farmear” bayas
                    System.out.println("Bien, has encontrado bayas!!!!");
                    try {
                        equipService.añadirAlInventario(personaje.getId(), new Baya());
                        personaje = Utils.recargarPersonaje(personaje.getId());
                    } catch (ReglaJuegoException e) {
                        System.out.println("No puedes añadir bayas: " + e.getMessage());
                    }
                }
            }
            break;

			 case 2: {
                 // Cazar (combate + carne seca con service + recarga)
                 try {
                     int expAntes = personaje.getExperiencia();
                     System.out.println("Intentando cazar...");
                     personaje = Utils.cazar(personaje);

                     if (personaje.getExperiencia() > expAntes) {
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
                 // Fabricar con service (ya no Utils.construirArma)
                 try {
                     Utils.menuFabricar(personaje);
                     personaje = Utils.recargarPersonaje(personaje.getId());
                 } catch (Exception e) {
                     System.out.println("No puedes fabricar: " + e.getMessage());
                 }
             }
             break;

             case 4: {
                 // Usar trampa: revisar inventario para ver si hay trampas
                 int contadorTrampas = 0;
                 for (Equipamiento eq : personaje.getEquipo()) {
                     if (eq instanceof Trampa) contadorTrampas++;
                 }

                 if (contadorTrampas == 0) {
                     System.out.println("No tienes trampas en tu inventario.");
                     break;
                 }

                 // Si hay trampas y aún no has derrotado al lobo, aparece el lobo
                 if (!controladorAtaqueLobo) {
                     System.out.println(
                         "Bien, has atrapado un conejo!!!! te acercas despacio pero..." +
                         " sientes como algo te acecha... te ataca un lobo que también quiere el conejo."
                     );

                     Lobo lobo = new Lobo();
                     int expAntes = personaje.getExperiencia();

                     boolean ganado = Utils.combate(personaje, lobo);

                     if (ganado && personaje.getExperiencia() > expAntes) {
                         System.out.println("Has sobrevivido al ataque del lobo y conseguido el conejo.");

                         controladorAtaqueLobo = true;

                         try {
                             equipService.añadirAlInventario(personaje.getId(), new CarneSeca());
                             personaje = Utils.recargarPersonaje(personaje.getId());
                         } catch (ReglaJuegoException e) {
                             System.out.println("No puedes añadir carne seca: " + e.getMessage());
                         }

                         bosqueOscurokey1 = true;
                     }

                 } else {
                     // Ya derrotado: “farmear” carne seca
                     System.out.println("Bien has atrapado un conejo!!!!");
                     try {
                         equipService.añadirAlInventario(personaje.getId(), new CarneSeca());
                         personaje = Utils.recargarPersonaje(personaje.getId());
                     } catch (ReglaJuegoException e) {
                         System.out.println("No puedes añadir carne seca: " + e.getMessage());
                     }
                 }
             }
             break;

             case 5: {
                 // Inventario
                 try {
                     Utils.menuInventario(personaje);
                     LOGGER.info("Mostrando inventario de: " + personaje.getNombre());
                     personaje = Utils.recargarPersonaje(personaje.getId()); // por equipar/consumir
                 } catch (Exception e) {
                     LOGGER.log(Level.SEVERE, "Error al mostrar el inventario", e);
                     System.out.println("No se pudo mostrar el inventario.");
                 }
             }
             break;

             case 6: {
                 // Buscar materiales (service + recarga)
                 try {
                     personaje = Utils.buscarObjeto(personaje);
                     LOGGER.info("El personaje " + personaje.getNombre() + " ha buscado un objeto.");
                 } catch (Exception e) {
                     LOGGER.log(Level.SEVERE, "Error al buscar objeto", e);
                     System.out.println("No se pudo buscar el objeto.");
                 }
             }
             break;

             case 7: {
                 if (bosqueOscurokey1 && bosqueOscurokey2 && bosqueOscurokey3) {
                     salida = true;
                     System.out.println("Ya puedes ir al río.");
                 } else {
                     System.out.println("Aún no cumples los requisitos para avanzar.");
                 }
             }
             break;

             case 8: {
                 // Descansar y recuperar vida (en memoria, se guarda al final)
                 Utils.recuperarVida(personaje);
                 System.out.println("Has descansado y recuperado toda la vida.");
                 LOGGER.info("Descanso. Personaje: " + personaje.getNombre());
                 bosqueOscurokey2 = true;
             }
             break;

             case 9: {
                 if (controladorAtaqueLobo && controladorJabali) {
                     System.out.println("Ya puedes invocar a tu lobo o jabalí compañero.");
                     bosqueOscurokey3 = true;
                     Utils.invocarLoboJabali(personaje);
                     personaje = Utils.recargarPersonaje(personaje.getId()); // por si lo persistes luego
                 } else {
                     System.out.println("Aún no has derrotado a un lobo y un jabalí, no puedes invocarlos.");
                 }
             }
             break;

             default:
                 System.out.println("Opción no válida");
         }

		} while (!salida);
	}

}
