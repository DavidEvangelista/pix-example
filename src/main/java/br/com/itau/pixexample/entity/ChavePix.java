package br.com.itau.pixexample.entity;

import br.com.itau.pixexample.entity.enums.TipoChave;
import br.com.itau.pixexample.entity.enums.TipoConta;
import br.com.itau.pixexample.entity.enums.TipoPessoa;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.Type;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.util.UUID;

@Data
@Builder
@Entity
@Table(name = "TB_CHAVE_PIX")
@AllArgsConstructor
@NoArgsConstructor
public class ChavePix {

    @Id
    @GeneratedValue(generator = "UUID")
    @GenericGenerator(
            name = "UUID",
            strategy = "org.hibernate.id.UUIDGenerator"
    )
    @Type(type = "uuid-char")
    private UUID id;
    private TipoChave tipoChave;
    private String valorChave;
    private Integer numeroAgencia;
    private Integer numeroConta;
    private TipoConta tipoConta;
    private String nomeCorrentista;
    private String sobrenomeCorrentista;
    private TipoPessoa tipoPessoa;
    private LocalDateTime dataCriacao;
    private LocalDateTime dataInativacao;
    private Boolean ativo = Boolean.TRUE;
}
