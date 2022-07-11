package br.com.itau.pixexample.exception.dto;

import lombok.*;

import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;

@Getter
@ToString
@Builder
@AllArgsConstructor
@NoArgsConstructor
public class ResponseErrorDto {

    @NotNull
    private String message;

    @NotNull
    private String path;

    @Min(0)
    private Integer statusCode;

    @NotNull
    private String statusMessage;
}
