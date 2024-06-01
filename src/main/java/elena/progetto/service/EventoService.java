package elena.progetto.service;

import elena.progetto.DTO.EventoDto;
import elena.progetto.entity.Evento;
import elena.progetto.entity.Utente;
import elena.progetto.exceptions.EventoNonTrovatoException;
import elena.progetto.exceptions.NumMaxPartecipantiRaggiunto;
import elena.progetto.repository.EventoRepository;
import elena.progetto.repository.UtenteRepository;
import jakarta.transaction.Transactional;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
public class EventoService {

    @Autowired
    private EventoRepository eventoRepository;

    @Autowired
    private UtenteRepository utenteRepository;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public List<Evento> getAllEventi() {
        return eventoRepository.findAll();
    }

    public Optional<Evento> getEventoById(int id) {
        return eventoRepository.findById(id);
    }

    @Transactional
    public String salvaEvento(EventoDto eventoDto) {
        Evento evento = new Evento();
        evento.setData(eventoDto.getData());
        evento.setTitolo(eventoDto.getTitolo());
        evento.setDescrizione(eventoDto.getDescrizione());
        evento.setMaxPosti(eventoDto.getMaxPosti());
        evento.setLuogo(eventoDto.getLuogo());

        List<Utente> partecipanti = utenteRepository.findByIdIn(eventoDto.getPartecipanti());

        if (partecipanti.size() > evento.getMaxPosti()) {
            throw new NumMaxPartecipantiRaggiunto("Il numero massimo di partecipanti è già stato raggiunto per questo evento");
        }

        for (Utente partecipante : partecipanti) {
            partecipante.getEventi().add(evento);
        }

        evento.setPartecipanti(partecipanti);

        eventoRepository.save(evento);

        for (Utente partecipante : partecipanti) {
            utenteRepository.save(partecipante);
        }

        evento.setNumeroPostiDisponibili(evento.getMaxPosti() - evento.getPartecipanti().size());

        return "Evento con ID:" + evento.getId() + " è stato salvato correttamente";
    }

    public Evento aggiornaEvento(int id, EventoDto eventoDto) {
        Optional<Evento> eventoOptional = getEventoById(id);

        if (eventoOptional.isPresent()) {
            Evento evento = eventoOptional.get();
            evento.setData(eventoDto.getData());
            evento.setLuogo(eventoDto.getLuogo());
            evento.setTitolo(eventoDto.getTitolo());
            evento.setDescrizione(eventoDto.getDescrizione());
            evento.setMaxPosti(eventoDto.getMaxPosti());

            //trovami tutti i partecipanti con l'ID passato
            List<Utente> partecipanti = utenteRepository.findByIdIn(eventoDto.getPartecipanti());

            // Uso il set e HashSet per evitare doppioni, siccome potrebberò mettermi due volte la stessa persona
            Set<Utente> utenti = new HashSet<>(partecipanti);

            // se i partecipanti inseriti sono maggiori dei posti massimi, lancia un'exception
            if (utenti.size() > evento.getMaxPosti()) {
                throw new NumMaxPartecipantiRaggiunto("Il numero massimo di partecipanti è già stato raggiunto per questo evento");
            }

            // pulisco prima tutti i partecipanti dalla lista
            evento.getPartecipanti().clear();

            // aggiungo tutti i partecipanti all'evento
            for (Utente partecipante : utenti) {
                partecipante.getEventi().add(evento);
            }

            evento.setPartecipanti(new ArrayList<>(utenti));

            evento.setNumeroPostiDisponibili(evento.getMaxPosti() - evento.getPartecipanti().size());

            return eventoRepository.save(evento);
        } else {
            throw new EventoNonTrovatoException("Non è possibile aggiornare l'evento siccome non è stato trovato");
        }
    }

    public String eliminaEvento(int id) {
        Optional<Evento> eventoOptional = getEventoById(id);

        if(eventoOptional.isPresent()) {
            eventoRepository.deleteById(id);
            return "Evento con ID:" + id + " è stato eliminato";
        } else {
            throw new EventoNonTrovatoException("Evento con ID: " + id + " non è stato trovato");
        }
    }
}
