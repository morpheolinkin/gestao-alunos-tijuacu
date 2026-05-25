package br.com.tijuacu.gestaoalunos.exception;

import lombok.Getter;
import lombok.Setter;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.List;

@Getter @Setter
public class ApiError {

    private String timestamp;
    private int status;
    private String error;   // ex.: "Not Found"
    private String message; // mensagem mais amigável
    private String path;
    private List<String> errors; // detalhes (ex.: campos inválidos)

    public ApiError(int status, String error, String message, String path, List<String> errors) {
        this.timestamp = formatNow();
        this.status = status;
        this.error = error;
        this.message = message;
        this.path = path;
        this.errors = errors;
    }

    private String formatNow() {
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd/MM/yyyy HH:mm:ss");
        return LocalDateTime.now().format(formatter);
    }
}