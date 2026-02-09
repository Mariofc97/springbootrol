package es.cursojava.springbootrol.repositories;

import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import es.cursojava.springbootrol.entities.criatura.Criatura;

@Repository
public interface CriaturaRepository extends JpaRepository<Criatura, Long> {
    List<Criatura> findByPersonajeId(Long personajeId);
}
