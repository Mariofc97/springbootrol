package service;

import java.util.List;

import model.CriaturaDto;
import exceptions.ReglaJuegoException;

public interface CriaturaService {

    CriaturaDto invocarCompanero(Long personajeId, String tipoCriatura, String alias) throws ReglaJuegoException;

    List<CriaturaDto> listarPorPersonaje(Long personajeId) throws ReglaJuegoException;
}

