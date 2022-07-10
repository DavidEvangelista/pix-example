package br.com.itau.pixexample.service;

import br.com.itau.pixexample.entity.ChavePix;
import br.com.itau.pixexample.exception.BusinessException;
import br.com.itau.pixexample.repository.ChavePixRepository;
import br.com.itau.pixexample.repository.QuerySearchRepository;
import br.com.itau.pixexample.service.enums.StrategyEnum;
import br.com.itau.pixexample.util.CpfCnpjUtil;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Component;

@Component
public class ChavePixCpfCnpjStrategy extends ChavePixService implements ChavePixStrategy {

    public ChavePixCpfCnpjStrategy(ChavePixRepository repository, @Qualifier("quertSearchRepositoryImpl") QuerySearchRepository querySearchRepository) {
        super(repository, querySearchRepository);
    }

    @Override
    public void businessValidation(ChavePix entity) {
        var isValid = CpfCnpjUtil.isValid(entity.getValorChave());
        if(!isValid) {
            throw new BusinessException("Cpf ou CNPJ inválido");
        }
    }

    @Override
    public StrategyEnum getStrategy() {
        return StrategyEnum.CPF_CNPJ;
    }
}
