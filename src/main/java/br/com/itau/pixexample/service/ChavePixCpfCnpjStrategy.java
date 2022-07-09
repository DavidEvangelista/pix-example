package br.com.itau.pixexample.service;

import br.com.itau.pixexample.entity.ChavePix;
import br.com.itau.pixexample.entity.dto.ChavePixDTO;
import br.com.itau.pixexample.exception.BusinessException;
import br.com.itau.pixexample.repository.ChavePixRepository;
import br.com.itau.pixexample.service.enums.StrategyEnum;
import br.com.itau.pixexample.util.CpfCnpjUtil;
import org.modelmapper.ModelMapper;
import org.springframework.stereotype.Component;

@Component
public class ChavePixCpfCnpjStrategy extends ChavePixService implements ChavePixStrategy {

    public ChavePixCpfCnpjStrategy(ChavePixRepository repository, ModelMapper modelMapper) {
        super(repository, modelMapper);
    }

    @Override
    public void businessValidation(ChavePix entity) {
        Boolean isValid = CpfCnpjUtil.isValid(entity.getValorChave());
        if(!isValid) {
            throw new BusinessException("Cpf ou CNPJ inválido");
        }
    }

    @Override
    public StrategyEnum getStrategy() {
        return StrategyEnum.CPF_CNPJ;
    }
}
