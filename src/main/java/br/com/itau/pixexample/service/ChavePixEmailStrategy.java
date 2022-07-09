package br.com.itau.pixexample.service;

import br.com.itau.pixexample.entity.ChavePix;
import br.com.itau.pixexample.entity.dto.ChavePixDTO;
import br.com.itau.pixexample.exception.BusinessException;
import br.com.itau.pixexample.repository.ChavePixRepository;
import br.com.itau.pixexample.service.enums.StrategyEnum;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

import java.util.regex.Pattern;

@Component
public class ChavePixEmailStrategy extends ChavePixService implements ChavePixStrategy {

    public ChavePixEmailStrategy(ChavePixRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
    }

    @Override
    public void businessValidation(ChavePix entity) {
        Boolean isValid = Pattern
                .compile("^(?=.{1,64}@)[A-Za-z0-9_-]+(\\.[A-Za-z0-9_-]+)*@"
                        + "[^-][A-Za-z0-9-]+(\\.[A-Za-z0-9-]+)*(\\.[A-Za-z]{2,})$")
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
