package es.cursojava.springbootrol.repositories;

import java.util.Optional;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import es.cursojava.springbootrol.entities.Personaje;

@Repository
public interface PersonajeRepository extends JpaRepository<Personaje, Long> {
    
    List<Personaje> findByUsuarioId(Long usuarioId);

    @Query("SELECT DISTINCT p FROM Personaje p LEFT JOIN FETCH p.equipo WHERE p.id = :id")
    Optional<Personaje> findByIdFetchAll(Long id);
    
    // To delete by id and usuarioId safely
    void deleteByIdAndUsuarioId(Long id, Long usuarioId);
    
    Optional<Personaje> findByIdAndUsuarioId(Long id, Long usuarioId);
}
