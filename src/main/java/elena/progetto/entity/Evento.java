package elena.progetto.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import jakarta.persistence.*;
import lombok.Data;
import lombok.ToString;

import java.time.LocalDate;
import java.util.List;

@Data
@Entity(name = "eventi")
@ToString(exclude = "partecipanti")
public class Evento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int id;

    private String titolo;

    private String descrizione;

    private LocalDate data;

    private String luogo;

    private int maxPosti;

    private int numeroPostiDisponibili;

    @ManyToMany(mappedBy = "eventi")
    @JsonIgnore
    private List<Utente> partecipanti;
}
