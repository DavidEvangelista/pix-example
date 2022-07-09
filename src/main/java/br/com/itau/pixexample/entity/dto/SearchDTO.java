package br.com.itau.pixexample.entity.dto;

import br.com.itau.pixexample.entity.enums.TipoChave;
import lombok.Data;

import java.time.LocalDateTime;

@Data
public class SearchDTO {
    private TipoChave tipoChave;
    private Integer agencia;
    private Integer conta;
    private String correntista;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataInativaca;
}
