package br.com.tijuacu.gestaoalunos.exception;

import jakarta.servlet.http.HttpServletRequest;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;

import java.util.List;

@RestControllerAdvice
public class GlobalExceptionHandler {

    // 1. Agrupamos todas as exceções de "Entidade Não Encontrada" aqui (Erro 404)
    @ExceptionHandler({
            AlunoNaoEncontradoException.class,
            TurmaNaoEncontradaException.class,
            MatriculaNaoEncontradaException.class,
            UsuarioNaoEncontradoException.class
    })
    public ResponseEntity<ApiError> handleEntidadeNaoEncontrada(
            RuntimeException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.NOT_FOUND;

        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(status).body(apiError);
    }

    // 2. Tratamento para quebra de regra de negócio (Erro 409 - Conflict)
    @ExceptionHandler(MatriculaAtivaJaExistenteException.class)
    public ResponseEntity<ApiError> handleMatriculaAtivaJaExistente(
            MatriculaAtivaJaExistenteException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.CONFLICT;

        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                ex.getMessage(),
                request.getRequestURI(),
                null
        );

        return ResponseEntity.status(status).body(apiError);
    }

    // 3. Tratamento de validação de campos (Erro 400 - Bad Request)
    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<ApiError> handleValidationException(
            MethodArgumentNotValidException ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.BAD_REQUEST;

        List<String> errors = ex.getBindingResult()
                .getFieldErrors()
                .stream()
                .map(this::formatFieldError)
                .toList();

        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                "Erro de validação nos campos",
                request.getRequestURI(),
                errors
        );

        return ResponseEntity.status(status).body(apiError);
    }

    private String formatFieldError(FieldError fieldError) {
        return fieldError.getField() + ": " + fieldError.getDefaultMessage();
    }

    // 4. Erros genéricos não mapeados (Erro 500)
    @ExceptionHandler(Exception.class)
    public ResponseEntity<ApiError> handleGenericException(
            Exception ex,
            HttpServletRequest request
    ) {
        HttpStatus status = HttpStatus.INTERNAL_SERVER_ERROR;

        ApiError apiError = new ApiError(
                status.value(),
                status.getReasonPhrase(),
                "Ocorreu um erro interno no servidor",
                request.getRequestURI(),
                List.of(ex.getMessage())
        );

        return ResponseEntity.status(status).body(apiError);
    }
}