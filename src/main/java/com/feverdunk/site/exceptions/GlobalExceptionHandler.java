package com.feverdunk.site.exceptions;

import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.extern.slf4j.Slf4j;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.http.*;
import org.springframework.lang.NonNull;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.AuthenticationFailureHandler;
import org.springframework.validation.BindingResult;

import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.RestControllerAdvice;
import org.springframework.web.context.request.WebRequest;
import org.springframework.web.servlet.mvc.method.annotation.ResponseEntityExceptionHandler;

import java.io.IOException;
import java.nio.file.AccessDeniedException;

@Slf4j(topic = "GLOBAL_EXCEPTION_HANLDER")
@RestControllerAdvice
public class GlobalExceptionHandler extends ResponseEntityExceptionHandler implements AuthenticationFailureHandler {

    public GlobalExceptionHandler() {
    }

    @ExceptionHandler(Exception.class)
    public ResponseEntity<Object> handleAllUncaughtExceptions(Exception ex, WebRequest request) {
        String message = "Unknow Server Error.";
        log.error("Unknow Server Error.", ex);
        ProblemDetail body = this.createProblemDetail(ex, HttpStatus.INTERNAL_SERVER_ERROR, "Unknow Server Error.", (String)null, (Object[])null, request);
        return this.handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.INTERNAL_SERVER_ERROR, request);
    }

    protected ResponseEntity<Object> handleMethodArgumentNotValid(MethodArgumentNotValidException ex, @NonNull HttpHeaders headers, @NonNull HttpStatusCode status, @NonNull WebRequest request) {
        BindingResult bindingResult = ex.getBindingResult();
        StringBuilder sb = new StringBuilder();
        sb.append("Objeto: ").append(bindingResult.getObjectName()).append(" Erro: ");

        try {
            sb.append(ex.getFieldError().getField()).append(" ").append(ex.getFieldError().getDefaultMessage()).append("]");
        } catch (NullPointerException var9) {
            sb.append("Desconhecido");
        }

        String message = sb.toString();
        log.error(message, ex);
        ProblemDetail body = this.createProblemDetail(ex, HttpStatus.BAD_REQUEST, message, (String)null, (Object[])null, request);
        return this.handleExceptionInternal(ex, body, headers, status, request);
    }

    @ExceptionHandler(DataIntegrityViolationException.class)
    public ResponseEntity<Object> handleDataIntegrityViolationException(DataIntegrityViolationException ex, WebRequest request) {
        String message = ex.getMostSpecificCause().getMessage();
        log.error(message, ex);
        ProblemDetail body = this.createProblemDetail(ex, HttpStatus.BAD_REQUEST, message, (String)null, (Object[])null, request);
        return this.handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.BAD_REQUEST, request);
    }

    @ExceptionHandler(AuthenticationException.class)
    public ResponseEntity<Object> handleAuthenticationException(AuthorizationException ex, WebRequest request) {
        String message = ex.getMessage();
        log.error(message, ex);
        ProblemDetail body = this.createProblemDetail(ex, HttpStatus.FORBIDDEN, message, (String)null, (Object[])null, request);
        return this.handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(AccessDeniedException.class)
    public ResponseEntity<Object> handleAccessDeniedException(AccessDeniedException ex, WebRequest request) {
        String message = ex.getMessage();
        log.error(message, ex);
        ProblemDetail body = this.createProblemDetail(ex, HttpStatus.FORBIDDEN, message, (String)null, (Object[])null, request);
        return this.handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.FORBIDDEN, request);
    }

    @ExceptionHandler(AuthorizationException.class)
    public ResponseEntity<Object> handleAuthorizationException(AuthorizationException ex, WebRequest request) {
        String message = ex.getMessage();
        log.error(message, ex);
        ProblemDetail body = this.createProblemDetail(ex, HttpStatus.UNAUTHORIZED, message, (String)null, (Object[])null, request);
        return this.handleExceptionInternal(ex, body, new HttpHeaders(), HttpStatus.UNAUTHORIZED, request);
    }

    public void onAuthenticationFailure(HttpServletRequest request, HttpServletResponse response, AuthenticationException exception) throws IOException, ServletException {
        response.setStatus(HttpStatus.UNAUTHORIZED.value());
        response.setContentType("application/json");
    }
}
