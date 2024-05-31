package elena.progetto.service;

import elena.progetto.DTO.EventoDto;
import elena.progetto.entity.Evento;
import elena.progetto.entity.Utente;
import elena.progetto.exceptions.EventoNonTrovatoException;
import elena.progetto.exceptions.NumMaxPartecipantiRaggiunto;
import elena.progetto.repository.EventoRepository;
import elena.progetto.repository.UtenteRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Optional;

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

    public String salvaEvento(EventoDto eventoDto) {
        Evento evento = new Evento();
        evento.setData(eventoDto.getData());
        evento.setTitolo(eventoDto.getTitolo());
        evento.setDescrizione(eventoDto.getDescrizione());
        Utente partecipante = (Utente) eventoDto.getPartecipanti();
        evento.getPartecipanti().add(partecipante);
        evento.setMaxPosti(eventoDto.getMaxPosti());
        evento.setNumeroPostiDisponibili(eventoDto.getNumeroPostiDisponibili());
        evento.setLuogo(eventoDto.getLuogo());
        eventoRepository.save(evento);

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

            if (evento.getPartecipanti().size() >= evento.getMaxPosti()) {
                throw new NumMaxPartecipantiRaggiunto("Il numero massimo di partecipanti è già stato raggiunto per questo evento");
            }

            Utente partecipante = (Utente) eventoDto.getPartecipanti();
            evento.getPartecipanti().add(partecipante);
            evento.setNumeroPostiDisponibili(evento.getMaxPosti() - evento.getPartecipanti().size());

            return eventoRepository.save(evento);
        } else {
            throw new EventoNonTrovatoException("Non è possibile aggiornare l'evento siccome non è stato trovato");
        }
    }

    public String eliminaEvento (int id) {
        Optional<Evento> eventoOptional = getEventoById(id);

        if(eventoOptional.isPresent()) {
            eventoRepository.deleteById(id);
            return "Evento con ID:" + id+ " è stato eliminato";
        } else {
            throw new EventoNonTrovatoException("Evento con ID:" + id+ " non è stato trovato");
        }
    }
}
