package es.cursojava.springbootrol.repositories;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;

import es.cursojava.springbootrol.entities.equipo.Equipamiento;

public interface EquipamientoRepository extends JpaRepository<Equipamiento, Long> {

    List<Equipamiento> findByPersonajeId(Long personajeId);

    @Modifying
    @Query("DELETE FROM Equipamiento e WHERE e.id = :id AND e.personaje.id = :personajeId")
    int deleteByIdAndPersonajeId(Long id, Long personajeId);
}
