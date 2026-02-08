package es.cursojava.springbootrol.service;

import java.util.List;

import es.cursojava.springbootrol.exceptions.ReglaJuegoException;
import es.cursojava.springbootrol.model.CriaturaDto;

public interface CriaturaService {

    CriaturaDto invocarCompanero(Long personajeId, String tipoCriatura, String alias) throws ReglaJuegoException;

    List<CriaturaDto> listarPorPersonaje(Long personajeId) throws ReglaJuegoException;
}

