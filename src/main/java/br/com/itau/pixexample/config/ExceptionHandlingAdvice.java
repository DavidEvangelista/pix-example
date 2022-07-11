package br.com.itau.pixexample.config;

import br.com.itau.pixexample.exception.BusinessException;
import br.com.itau.pixexample.exception.NotFoundException;
import br.com.itau.pixexample.exception.dto.ResponseErrorDto;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;

import javax.persistence.EntityNotFoundException;
import javax.servlet.http.HttpServletRequest;

import static org.springframework.http.HttpStatus.INTERNAL_SERVER_ERROR;
import static org.springframework.http.HttpStatus.NOT_FOUND;
import static org.springframework.http.HttpStatus.UNPROCESSABLE_ENTITY;

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
            EntityNotFoundException.class,
            NotFoundException.class
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

        var response = ResponseErrorDto.builder()
                .message(throwable.getMessage())
                .path(httpServletRequest.getRequestURI())
                .statusCode(httpStatus.value())
                .statusMessage(httpStatus.getReasonPhrase())
                .build();

        HttpHeaders headers = new HttpHeaders();
        headers.add(HttpHeaders.CONTENT_TYPE, "application/json; charset=UTF-8");

        return new ResponseEntity<>(response, headers, httpStatus);
    }

}
