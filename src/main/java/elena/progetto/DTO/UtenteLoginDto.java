package elena.progetto.DTO;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;

@Data
public class UtenteLoginDto {

    @Email
    @NotBlank(message = "il campo email non può essere vuoto/mancante/con soli spazi")
    private String email;

    @NotBlank(message = "il campo password non può essere vuoto/mancante/con soli spazi")
    private String password;
}
