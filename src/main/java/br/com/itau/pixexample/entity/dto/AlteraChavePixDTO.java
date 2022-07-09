package br.com.itau.pixexample.entity.dto;

import br.com.itau.pixexample.entity.enums.TipoConta;
import br.com.itau.pixexample.entity.enums.TipoPessoa;
import lombok.Builder;
import lombok.Data;
import org.hibernate.validator.constraints.Range;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

@Data
@Builder
public class AlteraChavePixDTO {

    private String id;

    @NotNull(message = "tipoConta é obrigatório")
    private TipoConta tipoConta;

    @NotNull(message = "numeroAgencia é obrigatório")
    @Range(min = 1, max = 9999, message = "Limite de 4 digitos")
    private Integer numeroAgencia;

    @NotNull(message = "numeroConta é obrigatório")
    @Range(min = 1, max = 99999999, message = "Limite de 8 digitos")
    private Integer numeroConta;

    @NotBlank(message = "nomeCorrentista é obrigatório")
    private String nomeCorrentista;

    @NotNull(message = "tipoPessoa é obrigatório")
    private TipoPessoa tipoPessoa;

    private String sobrenomeCorrentista;

}
