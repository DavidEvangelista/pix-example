package br.com.itau.pixexample.entity.dto;

import br.com.itau.pixexample.entity.enums.TipoChave;
import br.com.itau.pixexample.entity.enums.TipoConta;
import br.com.itau.pixexample.entity.enums.TipoPessoa;
import lombok.Data;

import javax.validation.constraints.Max;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;
import java.time.LocalDateTime;

@Data
public class ChavePixDTO {

    private String id;

    @NotNull
    private TipoChave tipoChave;

    @NotBlank(message = "valorChave é obrigatório")
    private String valorChave;

    @NotNull
    private TipoConta tipoConta;

    @NotNull
    @Max(4)
    private Integer numeroAgencia;

    @NotNull
    @Max(8)
    private Integer numeroConta;

    @NotBlank(message = "nomeCorrentista é obrigatório")
    private String nomeCorrentista;

    @NotNull
    private TipoPessoa tipoPessoa;

    private String sobrenomeCorrentista;

    private LocalDateTime dataCriacao;
    private LocalDateTime dataInativacao;

}
