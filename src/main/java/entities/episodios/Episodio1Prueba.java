package entities.episodios;

import java.util.logging.Level;
import java.util.logging.Logger;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import entities.Personaje;
import entities.equipo.objetos.HojaParaLimpiar;
import service.PersonajeService;
import utilidades.Utils;

@Service
public class Episodio1Prueba {

    private static final Logger LOGGER = Logger.getLogger(Episodio1Prueba.class.getName());

    @Autowired
    private PersonajeService personajeService;

    static {
        LOGGER.setUseParentHandlers(false);
    }

    public void episodio1(Personaje personaje, AccionesEpisodio acciones) {

        if (personaje == null) {
            LOGGER.warning("Se llamó a episodio1 con Personaje null");
            acciones.add("Error: personaje no proporcionado.");
            return;
        }

        if (personaje.getEquipo() == null) {
            try {
                personaje.setEquipo(new java.util.ArrayList<>());
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "No se pudo inicializar la lista de equipo", e);
            }
        }

        if (personaje.getCriaturas() == null) {
            try {
                personaje.setCriaturas(new java.util.ArrayList<>());
            } catch (Exception e) {
                LOGGER.log(Level.WARNING, "No se pudo inicializar la lista de criaturas", e);
            }
        }

        boolean key1 = false;
        boolean key2 = false;
        boolean key3 = false;

        boolean salida = false;
        int errorCount = 0;
        final int MAX_ERRORS = 3;

        acciones.add("EPISODIO 1: La Cueva");
        acciones.add("Despiertas en una cueva sin recordar tu nombre... pero sí tu olor.");
        acciones.add("Una voz te susurra: 'Para sobrevivir necesitas llorar, pensar y NO morir.'");

        do {
            try {
                int opcion = (int) (Math.random() * 5) + 1;

                switch (opcion) {

                case 1: {
                    int cantidad = Utils.contarHojas(personaje);
                    if (cantidad >= 5) {
                        acciones.add("Intentaste llorar, pero te dieron una torta por llorón.");
                        break;
                    }

                    personaje.getEquipo().add(new HojaParaLimpiar());
                    acciones.add("Lloraste desconsoladamente y obtuviste una Hoja de Ortiga.");
                    key1 = true;
                }
                    break;

                case 2: {
                    try {
                        int antes = personaje.getCriaturas().size();
                        Utils.invocacionCompañeroCriatura(personaje);
                        int despues = personaje.getCriaturas().size();

                        if (despues > antes) {
                            acciones.add("Invocaste con éxito un compañero de viaje.");
                            key2 = true;
                        } else {
                            acciones.add("Intentaste invocar una criatura, pero fallaste.");
                        }

                    } catch (Exception e) {
                        acciones.add("Error al intentar invocar una criatura.");
                    }
                }
                    break;

                case 3: {
                    try {
                        int vida = personaje.getPuntosVida();
                        if (vida <= 1) {
                            acciones.add("Intentaste salir con 1 de vida. No puedes. Debes dormir antes.");
                            break;
                        }

                        if (key1 && key2 && key3) {
                            personajeService.actualizar(personaje);
                            acciones.add("Has cumplido todos los requisitos. Sales de la cueva.");
                            salida = true;
                            break;
                        }

                        personaje.setPuntosVida(1);
                        String desgracia = Utils.desgraciaAleatorio();
                        acciones.add("Intentaste salir, pero sufriste una desgracia: " + desgracia);
                        acciones.add("Tu vida queda reducida a 1.");
                        key3 = true;

                    } catch (Exception e) {
                        acciones.add("Error al intentar salir de la cueva.");
                    }
                }
                    break;

                case 4: {
                    Utils.recuperarVida(personaje);
                    acciones.add("Dormiste profundamente y recuperaste toda la vida.");
                    key3 = true;
                }
                    break;

                case 5: {
                    try {
                        personaje = Utils.buscarObjeto(personaje);
                        acciones.add("Buscaste materiales en la cueva.");
                    } catch (Exception e) {
                        acciones.add("Error al buscar materiales.");
                    }
                }
                    break;

                default:
                    acciones.add("Opción inválida generada.");
                }

                errorCount = 0;

            } catch (Throwable t) {
                acciones.add("Error inesperado durante el episodio.");
                t.printStackTrace();
                errorCount++;

                if (errorCount >= MAX_ERRORS) {
                    acciones.add("Demasiados errores consecutivos. Episodio abortado.");
                    salida = true;
                }
            }

        } while (!salida);

        acciones.add("Fin del episodio 1.");
        personajeService.actualizar(personaje);
    }
}
