package elena.progetto.repository;

import elena.progetto.entity.Utente;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.List;
import java.util.Optional;

public interface UtenteRepository extends JpaRepository<Utente, Integer> {

    //la uso in authService
    public Optional<Utente> findByEmail(String email);

    //la uso in utenteService
    List<Utente> findByIdIn(List<Integer> ids);
}
