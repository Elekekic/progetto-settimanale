package elena.progetto.repository;

import elena.progetto.entity.Evento;
import org.springframework.data.jpa.repository.JpaRepository;


public interface EventoRepository extends JpaRepository<Evento, Integer> {
}
