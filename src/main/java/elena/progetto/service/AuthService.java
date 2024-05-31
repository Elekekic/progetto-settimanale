package elena.progetto.service;

import elena.progetto.DTO.UtenteLoginDto;
import elena.progetto.entity.Utente;
import elena.progetto.exceptions.UnathorizedException;
import elena.progetto.security.JwtTool;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthService {

    @Autowired
    private UtenteService utenteService;

    @Autowired
    private JwtTool jwtTool;

    @Autowired
    private PasswordEncoder passwordEncoder;

    public String authenticateUserandCreateToken(UtenteLoginDto utenteLoginDto) {
        Utente utente = utenteService.getUserByEmail(utenteLoginDto.getEmail());

        if(passwordEncoder.matches(utenteLoginDto.getPassword(), utente.getPassword())) {
            return jwtTool.createToken(utente);
        } else {
            throw new UnathorizedException("Error in authorization, relogin!");
        }
    }
}
