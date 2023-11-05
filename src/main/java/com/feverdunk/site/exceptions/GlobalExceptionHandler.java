package com.feverdunk.site.exceptions;

import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.validation.BindingResult;
import org.springframework.validation.FieldError;
import org.springframework.validation.ObjectError;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.util.Objects;

@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtExceptions(Exception ex, WebRequest request){
        final String message = "Unknow Server Error.";
        ProblemDetail body = createProblemDetail(ex, HttpStatus.INTERNAL_SERVER_ERROR,
                message, null, null, request);
        return handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    @Override
    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex,
                                                                  @NonNull HttpHeaders headers,
                                                                  @NonNull HttpStatusCode status,
                                                                  @NonNull WebRequest request){

        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        sb.append("Objeto: ").append(bindingResult.getObjectName()).append(" Erro: ");
        try{
            sb.append(ex.getFieldError().getField()).append(" ")
                    .append(ex.getFieldError().getDefaultMessage()).append("]");
        }catch (NullPointerException e){
            sb.append("Desconhecido");
        }
        final String message = sb.toString();
        ProblemDetail body = createProblemDetail(ex, HttpStatus.BAD_REQUEST,
                message, null, null, request);
        return handleExceptionInternal(ex, body, headers, status, request);
    }
}
