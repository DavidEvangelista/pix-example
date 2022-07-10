package br.com.itau.pixexample.service;

import br.com.itau.pixexample.entity.ChavePix;
import br.com.itau.pixexample.exception.BusinessException;
import br.com.itau.pixexample.repository.ChavePixRepository;
import br.com.itau.pixexample.repository.QuerySearchRepository;
import br.com.itau.pixexample.service.enums.StrategyEnum;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ChavePixEmailStrategy extends ChavePixService implements ChavePixStrategy {

    public ChavePixEmailStrategy(ChavePixRepository repository, @Qualifier("quertSearchRepositoryImpl") QuerySearchRepository querySearchRepository) {
        super(repository, querySearchRepository);
    }

    @Override
    public void businessValidation(ChavePix entity) {
        var isValid = Pattern
                .compile("^[A-Z0-9._%+-]+@[A-Z0-9.-]+\\\\.[A-Z]{2,6}$")
                .matcher(entity.getValorChave())
                .matches();
        if(!isValid || entity.getValorChave().length() > 77) {
            throw new BusinessException();
        }
    }

    @Override
    public StrategyEnum getStrategy() {
        return StrategyEnum.EMAIL;
    }
}
