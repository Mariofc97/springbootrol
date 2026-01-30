package service.impl;

import java.util.ArrayList;
import java.util.List;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import model.CriaturaDto;
import entities.Personaje;
import entities.criatura.Conejo;
import entities.criatura.Criatura;
import entities.criatura.Gusano;
import entities.criatura.Jabali;
import entities.criatura.Lobo;
import entities.criatura.Mosquito;
import entities.criatura.PezPrehistoricoGigante;
import entities.criatura.Raton;
import entities.criatura.Siluro;
import exceptions.ReglaJuegoException;
import repositories.CriaturaRepository;
import repositories.PersonajeRepository;
import service.CriaturaService;

@Service
public class CriaturaServiceImpl implements CriaturaService {

    private final PersonajeRepository personajeRepository;
    
    // We might not need CriaturaRepository if all access is via Personaje, but good to have injected if future needs.
    private final CriaturaRepository criaturaRepository;
    
    public CriaturaServiceImpl(PersonajeRepository personajeRepository, CriaturaRepository criaturaRepository) {
        this.personajeRepository = personajeRepository;
        this.criaturaRepository = criaturaRepository;
    }

    private static final int MAX_CRIATURAS = 5;
    private CriaturaDto mapToDto(Criatura c) {
        if (c == null) return null;

        String tipo = c.getClass().getSimpleName().toUpperCase();
        Long personajeId = (c.getPersonaje() != null) ? c.getPersonaje().getId() : null;

        return new CriaturaDto(
                c.getId(),
                tipo,
                c.getNombre(),
                c.getAlias(),
                c.getNivel(),
                c.getExperiencia(),
                c.getPuntosVida(),
                c.getPuntosAtaque(),
                c.getTipoAtaque(),
                personajeId
        );
    }

    private Criatura construirCriatura(String tipoCriatura) throws ReglaJuegoException {
        if (tipoCriatura == null || tipoCriatura.isBlank()) {
            throw new ReglaJuegoException("Tipo de criatura obligatorio.");
        }

        String t = tipoCriatura.trim().toUpperCase();

        if ("GUSANO".equals(t)) return new Gusano();
        if ("CONEJO".equals(t)) return new Conejo();
        if ("MOSQUITO".equals(t)) return new Mosquito();
        if ("RATON".equals(t)) return new Raton();
        if ("JABALI".equals(t)) return new Jabali();
        if ("LOBO".equals(t)) return new Lobo();
        if ("PEZ PREHISTORICO GIGANTE".equals(t)) return new PezPrehistoricoGigante();
        if ("SILURO".equals(t)) return new Siluro();

        throw new ReglaJuegoException("Tipo inválido: " + tipoCriatura + " (usa GUSANO / CONEJO / MOSQUITO / RATON / LOBO / PEZ PREHISTORICO GIGANTE/ SILURO)");
    }

    @Override
    @Transactional(readOnly = true)
    public List<CriaturaDto> listarPorPersonaje(Long personajeId) throws ReglaJuegoException {
        if (personajeId == null) throw new ReglaJuegoException("El personajeId es obligatorio.");

        Personaje p = personajeRepository.findByIdFetchAll(personajeId)
                .orElseThrow(() -> new ReglaJuegoException("No existe personaje con id=" + personajeId));

        List<Criatura> lista = p.getCriaturas();
        if (lista == null || lista.isEmpty()) return java.util.Collections.emptyList();

        List<CriaturaDto> res = new java.util.ArrayList<>();
        for (Criatura c : lista) res.add(mapToDto(c));
        return res;
    }
    
    @Override
    @Transactional
    public CriaturaDto invocarCompanero(Long personajeId, String tipoCriatura, String alias) throws ReglaJuegoException {

        // 1) validar entrada
        if (personajeId == null) throw new ReglaJuegoException("El id del personaje es obligatorio.");
        if (tipoCriatura == null || tipoCriatura.trim().isEmpty()) throw new ReglaJuegoException("El tipo de criatura es obligatorio.");

        // 2) cargar personaje con criaturas (importante para contar bien y evitar Lazy)
        Personaje p = personajeRepository.findByIdFetchAll(personajeId)
                .orElseThrow(() -> new ReglaJuegoException("No existe personaje con id=" + personajeId));

        if (p.getCriaturas() == null) p.setCriaturas(new ArrayList<>());

        // 3) regla: máximo criaturas
        if (p.getCriaturas().size() >= MAX_CRIATURAS) {
            throw new ReglaJuegoException("No puedes tener más de " + MAX_CRIATURAS + " compañeros.");
        }

        // 4) construir criatura por tipo
        String tipo = tipoCriatura.trim().toUpperCase();
        Criatura nueva = construirCriatura(tipo);

        // 5) alias (si viene vacío, ponemos el nombre base)
        if (alias == null || alias.trim().isEmpty()) {
            nueva.setAlias(nueva.getNombre());
        } else {
            nueva.setAlias(alias.trim());
        }

        // 6) enlazar FK usando el método seguro
        p.addCriatura(nueva);

        // 7) persistir: con cascade, basta update(p)
        personajeRepository.save(p);

        // 8) obtener la criatura persistida para devolver DTO
        // Como acabamos de añadir una, normalmente la última es la nueva:
        List<Criatura> lista = p.getCriaturas();
        Criatura persistida = lista.get(lista.size() - 1);

        return mapToDto(persistida);
    }
}

