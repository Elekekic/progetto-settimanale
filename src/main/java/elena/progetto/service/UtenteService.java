package elena.progetto.service;

import elena.progetto.DTO.UtenteDto;
import elena.progetto.entity.Ruoli;
import elena.progetto.entity.Utente;
import elena.progetto.exceptions.UtenteNonTrovatoException;
import elena.progetto.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

@Service
public class UtenteService {

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Utente> getAllUSers() {
        return utenteRepository.findAll();
    }

    public Optional<Utente> getUserById(int id) {
        return utenteRepository.findById(id);
    }

    public Utente getUserByEmail(String email) {
        Optional<Utente> utenteOptional = utenteRepository.findByEmail(email);

        if (utenteOptional.isPresent()) {
            return utenteOptional.get();
        } else {
            throw new UtenteNonTrovatoException("Utente con EMAIL:" + email + " non è stato trovato");
        }
    }

    public String salvaUtente(UtenteDto utenteDto) {
        Utente utente = new Utente();
        utente.setEmail(utenteDto.getEmail());
        utente.setNome(utenteDto.getNome());
        utente.setCognome(utenteDto.getCognome());
        utente.setRuolo(Ruoli.UTENTE_NORMALE);
        utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));
        utenteRepository.save(utente);

        return "Utente con ID:" + utente.getId() + " è stato salvato correttamente";
    }

    public Utente aggiornaUtente (int id, UtenteDto utenteDto) {
        Optional<Utente> utenteOptional= getUserById(id);

        if(utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();
            utente.setEmail(utenteDto.getEmail());
            utente.setNome(utenteDto.getNome());
            utente.setCognome(utenteDto.getCognome());
            utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));

            return utenteRepository.save(utente);
        } else {
            throw new UtenteNonTrovatoException("Non è possibile aggiornare l'utente siccome non è stato trovato");
        }
    }

    public String eliminaUtente ( int id) {
        Optional<Utente> utenteOptional = getUserById(id);

        if(utenteOptional.isPresent()) {
            utenteRepository.deleteById(id);
            return "User has been deleted";
        } else {
            throw new UtenteNonTrovatoException("Utente con ID:" + id+ " non è stato trovato");
        }
    }
}
