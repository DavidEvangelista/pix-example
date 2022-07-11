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

    @NotNull(message = "O campo tipoConta é obrigatório")
    private TipoConta tipoConta;

    @NotNull(message = "O campo numeroAgencia é obrigatório")
    @Range(min = 1, max = 9999, message = "O campo numeroAgencia têm um limite de 4 digitos")
    private Integer numeroAgencia;

    @NotNull(message = "O campo numeroConta é obrigatório")
    @Range(min = 1, max = 99999999, message = "O campo numeroConta têm um limite de 8 digitos")
    private Integer numeroConta;

    @NotBlank(message = "O campo nomeCorrentista é obrigatório")
    private String nomeCorrentista;

    @NotNull(message = "O campo tipoPessoa é obrigatório")
    private TipoPessoa tipoPessoa;

    private String sobrenomeCorrentista;

}
