package elena.progetto.repository;

import elena.progetto.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {

    public Optional<Utente> findByEmail(String email);

    List<Utente> findByIdIn(List<Integer> ids);
}
