package elena.progetto.DTO;

import elena.progetto.entity.Utente;
import jakarta.persistence.ManyToMany;
import jakarta.validation.constraints.Min;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.time.LocalDate;
import java.util.List;

@Data
public class EventoDto {

    @NotBlank(message = "il campo titolo non può essere vuoto/mancante/con soli spazi")
    private String titolo;

    private String descrizione;

    @NotBlank(message = "il campo data non può essere vuoto/mancante/con soli spazi")
    private LocalDate data;

    @NotBlank(message = "il campo luogo non può essere vuoto/mancante/con soli spazi")
    private String luogo;

    @NotNull(message = "i posti massimi non possono essere nulli")
    private Integer maxPosti;

    @NotNull(message = "i posti disponibili non possono essere nulli")
    @Min(value = 0, message = "Il numero di posti disponibili deve essere maggiore o uguale a zero (se i posti sono finiti)")
    private Integer numeroPostiDisponibili;


    private List<Utente> partecipanti;

}
