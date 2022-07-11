package br.com.itau.pixexample.config;

import br.com.itau.pixexample.exception.BusinessException;
import br.com.itau.pixexample.exception.dto.ResponseErrorDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.support.DefaultMessageSourceResolvable;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.beanvalidation.SpringValidatorAdapter;
import org.springframework.web.bind.MethodArgumentNotValidException;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import java.util.stream.Collectors;

import static org.springframework.http.HttpStatus.*;

@Slf4j
@ResponseBody
@ControllerAdvice
@AllArgsConstructor
class ExceptionHandlingAdvice {

    @ExceptionHandler(Throwable.class)
    @ResponseStatus(INTERNAL_SERVER_ERROR)
    ResponseEntity<ResponseErrorDto> handleInternalServerError(HttpServletRequest httpServletRequest, Throwable throwable) {
        return logStackTraceAndConstructResponseErrorDto(httpServletRequest, throwable, INTERNAL_SERVER_ERROR);
    }

    @ExceptionHandler({
            EntityNotFoundException.class
    })
    @ResponseStatus(NOT_FOUND)
    ResponseEntity<ResponseErrorDto> handleNotFound(HttpServletRequest httpServletRequest, Throwable throwable) {
        return logStackTraceAndConstructResponseErrorDto(httpServletRequest, throwable, NOT_FOUND);
    }

    @ExceptionHandler({ BusinessException.class })
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    ResponseEntity<ResponseErrorDto> handleUnprocessableEntity(HttpServletRequest httpServletRequest, Throwable throwable) {
        return logStackTraceAndConstructResponseErrorDto(httpServletRequest, throwable, UNPROCESSABLE_ENTITY);
    }

    @ExceptionHandler({ MethodArgumentNotValidException.class })
    @ResponseStatus(UNPROCESSABLE_ENTITY)
    ResponseEntity<ResponseErrorDto> handleArgumentNotValidEntity(HttpServletRequest httpServletRequest, Throwable throwable) {
        return logStackTraceAndConstructResponseErrorDto(httpServletRequest, throwable, UNPROCESSABLE_ENTITY);
    }


    private ResponseEntity<ResponseErrorDto> logStackTraceAndConstructResponseErrorDto(
            HttpServletRequest httpServletRequest,
            Throwable throwable,
            HttpStatus httpStatus) {

        log.error(
                "Erro @ {} {}, {} ({}), resultando em {}: {}",
                httpServletRequest.getMethod(),
                httpServletRequest.getRequestURI(),
                throwable.getClass().getSimpleName(),
                throwable.getMessage(),
                httpStatus.value(),
                httpStatus.getReasonPhrase(),
                throwable
        );

        var customMessage = throwable.getMessage();

        if(throwable instanceof MethodArgumentNotValidException) {
            customMessage = ((MethodArgumentNotValidException) throwable)
                    .getBindingResult()
                    .getAllErrors()
                    .stream()
                    .map(DefaultMessageSourceResolvable::getDefaultMessage)
                    .collect(Collectors.joining());
        }

        var response = ResponseErrorDto.builder()
                .message(customMessage)
                .path(httpServletRequest.getRequestURI())
                .statusCode(httpStatus.value())
                .statusMessage(httpStatus.getReasonPhrase())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        return new ResponseEntity<>(response, headers, httpStatus);
    }

}
