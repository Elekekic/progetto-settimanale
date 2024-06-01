package elena.progetto.exceptions;

import elena.progetto.entity.Error;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.time.LocalDateTime;

@RestControllerAdvice
public class CentralizedExceptionHandler extends ResponseEntityExceptionHandler {

    @ExceptionHandler(BadRequestException.class)
    public ResponseEntity<Object> BadRequestHandler(BadRequestException e){
        Error error= new Error();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        error.setStatoErrore(HttpStatus.BAD_REQUEST);

        return new ResponseEntity<>(error, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler(UnathorizedException.class)
    public ResponseEntity<Object> UnathorizedHandler(UnathorizedException e){
        Error error= new Error();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        error.setStatoErrore(HttpStatus.UNAUTHORIZED);

        return new ResponseEntity<>(error, HttpStatus.UNAUTHORIZED);
    }

    @ExceptionHandler(UtenteNonTrovatoException.class)
    public ResponseEntity<Object> UserNotFoundHandler(UtenteNonTrovatoException e){
        Error error= new Error();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        error.setStatoErrore(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(EventoNonTrovatoException.class)
    public ResponseEntity<Object> UserNotFoundHandler(EventoNonTrovatoException e){
        Error error= new Error();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        error.setStatoErrore(HttpStatus.NOT_FOUND);

        return new ResponseEntity<>(error, HttpStatus.NOT_FOUND);
    }

    @ExceptionHandler(NumMaxPartecipantiRaggiunto.class)
    public ResponseEntity<Object> UserNotFoundHandler(NumMaxPartecipantiRaggiunto e){
        Error error= new Error();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        error.setStatoErrore(HttpStatus.EXPECTATION_FAILED);

        return new ResponseEntity<>(error, HttpStatus.EXPECTATION_FAILED);
    }

    @ExceptionHandler(PostiEsauritiException.class)
    public ResponseEntity<Object> UserNotFoundHandler(PostiEsauritiException e){
        Error error= new Error();
        error.setMessage(e.getMessage());
        error.setDataErrore(LocalDateTime.now());
        error.setStatoErrore(HttpStatus.PRECONDITION_FAILED);

        return new ResponseEntity<>(error, HttpStatus.PRECONDITION_FAILED);
    }
}
