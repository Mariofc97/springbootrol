package service;

import java.util.List;

import model.EquipamientoDto;
import entities.equipo.Equipamiento;
import exceptions.ReglaJuegoException;

public interface EquipamientoService {

	//Añadir un equipo por "tipo" (CUERDA, PIEDRA, PALO...) al personaje
	EquipamientoDto añadirAlInventario(Long personajeId, Equipamiento nuevo) throws ReglaJuegoException;
	
	//Lista inventario del personaje
	List<EquipamientoDto> listarPorPersonaje(Long personajeId);
	
	EquipamientoDto fabricar(Long personajeId, String tipo) throws ReglaJuegoException;

	EquipamientoDto equiparArma(Long personajeId, Long equipamientoId) throws ReglaJuegoException;
	EquipamientoDto equiparEscudo(Long personajeId, Long equipamientoId) throws ReglaJuegoException;
	
	List<EquipamientoDto> listarConsumiblesCurativos(Long personajeId) throws ReglaJuegoException;
	
	int consumirCurativo(Long personajeId, Long equipamientoId) throws ReglaJuegoException;
	
	void eliminarDeInventario(Long personajeId, Long equipamientoId) throws ReglaJuegoException;

}
