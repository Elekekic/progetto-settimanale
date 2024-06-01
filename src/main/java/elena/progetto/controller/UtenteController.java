package elena.progetto.controller;

import elena.progetto.DTO.UtenteDto;
import elena.progetto.entity.Utente;
import elena.progetto.exceptions.BadRequestException;
import elena.progetto.exceptions.UtenteNonTrovatoException;
import elena.progetto.service.UtenteService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.validation.BindingResult;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/api")
public class UtenteController {

    @Autowired
    private UtenteService utenteService;

    @GetMapping("/utenti")
    @PreAuthorize("hasAnyAuthority('UTENTE_NORMALE','ORGANIZZATORE_DI_EVENTI')")
    public List<Utente> getAllUtenti() {
        return utenteService.getAllUtenti();
    }

    @GetMapping("/utenti/{id}")
    @PreAuthorize("hasAnyAuthority('UTENTE_NORMALE','ORGANIZZATORE_DI_EVENTI')")
    public Utente getUtenteById (@PathVariable int id) {
        Optional<Utente> userOptional = utenteService.getUtenteById(id);
        if(userOptional.isPresent()) {
            return userOptional.get();
        } else {
            throw new UtenteNonTrovatoException("Utente con ID: " + id + " non è ");
        }
    }

    @PutMapping("/utenti/{id}")
    @PreAuthorize("hasAnyAuthority('UTENTE_NORMALE','ORGANIZZATORE_DI_EVENTI')")
    public Utente aggiornaUtente (@PathVariable int id, @RequestBody @Validated UtenteDto utenteDto, BindingResult bindingResult) {
        if (bindingResult.hasErrors()) {
            throw new BadRequestException(bindingResult.getAllErrors().stream()
                    .map(objectError -> objectError.getDefaultMessage()).reduce("", (s, s2) -> s + s2));
        }
        return utenteService.aggiornaUtente(id, utenteDto);
    }

    @DeleteMapping("/utenti/{id}")
    @PreAuthorize("hasAnyAuthority('UTENTE_NORMALE','ORGANIZZATORE_DI_EVENTI')")
    public String eliminaUtente (@PathVariable int id) {
        return utenteService.eliminaUtente(id);
    }

    @PatchMapping("/utente/{utenteID}/eventi/{eventoID}")
    @PreAuthorize("hasAnyAuthority('UTENTE_NORMALE','ORGANIZZATORE_DI_EVENTI')")
    public String UtentesceglieEvento (@PathVariable int utenteID, @PathVariable int eventoID ) {
        return utenteService.utentesceglieEvento(eventoID, utenteID);
    }

    @PatchMapping("/eventi/{eventoID}/utente/{utenteID}")
    @PreAuthorize("hasAnyAuthority('UTENTE_NORMALE','ORGANIZZATORE_DI_EVENTI')")
    public String utenteSiToglieDaEvento (@PathVariable int utenteID, @PathVariable int eventoID ) {
        return utenteService.utenteSiToglieDaEvento(eventoID, utenteID);
    }
}
