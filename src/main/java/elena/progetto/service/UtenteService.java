package elena.progetto.service;

import elena.progetto.DTO.UtenteDto;
import elena.progetto.entity.Evento;
import elena.progetto.entity.Ruoli;
import elena.progetto.entity.Utente;
import elena.progetto.exceptions.*;
import elena.progetto.repository.EventoRepository;
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
    private EventoRepository eventoRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    // PRENDO TUTTI GLI UTENTI CHE CI SONO
    public List<Utente> getAllUtenti() {
        return utenteRepository.findAll();
    }

    // PRENDO L'UTENTE DAL SUO ID
    public Optional<Utente> getUtenteById(int id) {
        return utenteRepository.findById(id);
    }

    // PRENDO L'UTENTE DALLA SUA EMAIL
    public Utente getUtenteByEmail(String email) {
        Optional<Utente> utenteOptional = utenteRepository.findByEmail(email);

        if (utenteOptional.isPresent()) {
            return utenteOptional.get();
        } else {
            throw new UtenteNonTrovatoException("Utente con EMAIL: " + email + " non è stato trovato");
        }
    }

    // SALVO L'UTENTE
    public String salvaUtente(UtenteDto utenteDto) {
        Utente utente = new Utente();
        utente.setEmail(utenteDto.getEmail());
        utente.setNome(utenteDto.getNome());
        utente.setCognome(utenteDto.getCognome());

        //setto un ruolo già prestabilito
        utente.setRuolo(Ruoli.UTENTE_NORMALE);

        //proteggo la password
        utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));
        utenteRepository.save(utente);

        return "Utente con ID:" + utente.getId() + " è stato salvato correttamente";
    }

    // AGGIORNO L'UTENTE
    public Utente aggiornaUtente(int id, UtenteDto utenteDto) {
        Optional<Utente> utenteOptional = getUtenteById(id);

        if (utenteOptional.isPresent()) {
            Utente utente = utenteOptional.get();
            utente.setEmail(utenteDto.getEmail());
            utente.setNome(utenteDto.getNome());
            utente.setCognome(utenteDto.getCognome());

            //proteggo la password
            utente.setPassword(passwordEncoder.encode(utenteDto.getPassword()));

            return utenteRepository.save(utente);
        } else {
            throw new UtenteNonTrovatoException("Non è possibile aggiornare l'utente siccome non è stato trovato");
        }
    }

    // ELIMINO L'UTENTE
    public String eliminaUtente(int id) {
        Optional<Utente> utenteOptional = getUtenteById(id);

        if (utenteOptional.isPresent()) {
            utenteRepository.deleteById(id);
            return "Utente con ID: \" id \" è stato eliminato";
        } else {
            throw new UtenteNonTrovatoException("Utente con ID: " + id + " non è stato trovato");
        }
    }

    // EXTRA !!
    // piccola nota: non mi da nessun errore quando lo faccio, però non aggiunge effetivamente l'utente dall'evento
    // però l'ho voluto lasciare qua per mostrare che ci ho provato

//    public String utentesceglieEvento(int eventoID, int utenteID) {
//        Optional<Evento> eventoOptional = eventoRepository.findById(eventoID);
//        Optional<Utente> utenteOptional = utenteRepository.findById(utenteID);
//
//        if (eventoOptional.isPresent() && utenteOptional.isPresent()) {
//            Evento evento = eventoOptional.get();
//            Utente utente = utenteOptional.get();
//
                // se l'utente è già dentro l'evento, manda un'exception
//            if (evento.getPartecipanti().contains(utente)) {
//                throw new BadRequestException("L'utente con ID: " + utenteID + " è già presente nell'evento con ID: " + eventoID);
//            }
//
//            evento.getPartecipanti().add(utente);
//            evento.setNumeroPostiDisponibili(evento.getMaxPosti() - evento.getPartecipanti().size());
//            eventoRepository.save(evento);
//
//            return "Utente con ID: " + utenteID + " aggiunto all'evento con ID: " + eventoID;
//        } else {
//            throw new BadRequestException("Utente con ID: " + utenteID + " o evento con ID: " + eventoID + " non sono stati trovati");
//        }
//    }

    // EXTRA !!
    // piccola nota: non mi da nessun errore quando lo faccio, però non toglie l'utente dall'evento
//    public String utenteSiToglieDaEvento(int eventoID, int utenteID) {
//        Optional<Evento> eventoOptional = eventoRepository.findById(eventoID);
//        Optional<Utente> utenteOptional = utenteRepository.findById(utenteID);
//
//        if (eventoOptional.isPresent() && utenteOptional.isPresent()) {
//            Evento evento = eventoOptional.get();
//            Utente utente = utenteOptional.get();
//
                // se l'utente non è dentro l'evento, manda un'exception
//            if (!evento.getPartecipanti().contains(utente)) {
//                throw new UtenteNonTrovatoException("L'utente con ID: " + utenteID + " non è presente nell'evento con ID: " + eventoID);
//            }
//
//            evento.getPartecipanti().remove(utente);
//            evento.setNumeroPostiDisponibili(evento.getMaxPosti() - evento.getPartecipanti().size());
//            eventoRepository.save(evento);
//
//            return "Utente con ID: " + utenteID + " tolto dall'evento con ID: " + eventoID;
//        } else {
//            throw new BadRequestException("Utente con ID: " + utenteID + " o evento con ID: " + eventoID + " non sono stati trovati");
//        }
//    }
}
