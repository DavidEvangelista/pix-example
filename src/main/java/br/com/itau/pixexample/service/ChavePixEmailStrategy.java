package br.com.itau.pixexample.service;

import br.com.itau.pixexample.entity.ChavePix;
import br.com.itau.pixexample.exception.BusinessException;
import br.com.itau.pixexample.repository.ChavePixRepository;
import br.com.itau.pixexample.repository.QuerySearchRepository;
import br.com.itau.pixexample.service.enums.StrategyEnum;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ChavePixEmailStrategy extends ChavePixService implements ChavePixStrategy {

    private static final String EMAIL_PATTERN =
            "^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                    + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$";

    public ChavePixEmailStrategy(ChavePixRepository repository, QuerySearchRepository querySearchRepository) {
        super(repository, querySearchRepository);
    }

    @Override
    public void businessValidation(ChavePix entity) {
        var isValid = Pattern
                .compile(EMAIL_PATTERN)
                .matcher(entity.getValorChave())
                .matches();
        if(!isValid || entity.getValorChave().length() > 77) {
            throw new BusinessException("Email inválido");
        }
    }

    @Override
    public StrategyEnum getStrategy() {
        return StrategyEnum.EMAIL;
    }
}
