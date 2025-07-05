package com.baggaggio.individual_challenge.exceptions;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;

import java.util.HashMap;
import java.util.Map;

@ControllerAdvice
public class TratamentoDeErros {

    @ExceptionHandler(RecursoNaoEncontradoException.class)
    public ResponseEntity<String> recursoNaoEncontrado(RecursoNaoEncontradoException e) {
        // Retorna o status 404 e a mensagem da exceção
        return ResponseEntity.status(HttpStatus.NOT_FOUND).body(e.getMessage());
    }

    @ExceptionHandler(ConflitoDeRecursoException.class)
    public ResponseEntity<String> conflitoDeRecurso(ConflitoDeRecursoException e) {
        // Retorna o status 409 e a mensagem da exceção
        return ResponseEntity.status(HttpStatus.CONFLICT).body(e.getMessage());
    }

    @ExceptionHandler(MethodArgumentNotValidException.class)
    public ResponseEntity<Map<String, String>> handleValidationExceptions(MethodArgumentNotValidException ex) {
        // Cria um mapa para armazenar os erros no formato "nomeDoCampo": "mensagemDeErro"
        Map<String, String> errors = new HashMap<>();

        // Itera sobre todos os erros de campo encontrados na exceção
        ex.getBindingResult().getFieldErrors().forEach(error -> {
            // Adiciona o nome do campo e a mensagem de erro (definida na sua DTO) ao mapa
            errors.put(error.getField(), error.getDefaultMessage());
        });

        // Retorna o status 400 e o mapa de erros como o corpo da resposta em JSON
        return ResponseEntity.status(HttpStatus.BAD_REQUEST).body(errors);
    }
}
