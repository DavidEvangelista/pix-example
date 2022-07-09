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
public class ChavePixCelularStrategy extends ChavePixService implements ChavePixStrategy {

    public ChavePixCelularStrategy(ChavePixRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
    }

    @Override
    public void businessValidation(ChavePix entity) {
        Boolean isValid = Pattern
                .compile("^(\\+\\d{2}( )?)?((\\d{2})|\\d{3})[- .]?\\d{5}\\d{4}$")
                .matcher(entity.getValorChave())
                .matches();
        if(!isValid) {
            throw new BusinessException();
        }
    }

    @Override
    public StrategyEnum getStrategy() {
        return StrategyEnum.CELULAR;
    }
}
