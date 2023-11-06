package com.feverdunk.site.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.validation.BindingResult;

import org.springframework.validation.FieldError;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANDLER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements AuthenticationFailureHandler {
    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtExceptions(Exception ex, WebRequest request){
        final String message = "Unknow Server Error.";
        log.error(message, ex);
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
        log.error(message, ex);
        ProblemDetail body = createProblemDetail(ex, HttpStatus.BAD_REQUEST,
                message, null, null, request);
        return handleExceptionInternal(ex, body, headers, status, request);
    }
    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException exception,
                                                                        WebRequest request){
        final String message = exception.getMostSpecificCause().getMessage();
        log.error(message, exception);
        ProblemDetail body = createProblemDetail(exception, HttpStatus.BAD_REQUEST, message,
                null, null, request);
        return handleExceptionInternal(exception, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }


    @Override
    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.FORBIDDEN.value());
        response.setContentType("application/json");
    }
}
