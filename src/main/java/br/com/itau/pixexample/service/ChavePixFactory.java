package br.com.itau.pixexample.service;

import br.com.itau.pixexample.service.enums.StrategyEnum;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.EnumMap;
import java.util.Map;
import java.util.Set;
import java.util.stream.Stream;

@Component
public class ChavePixFactory {

    private Map<StrategyEnum, ChavePixStrategy> strategies;

    @Autowired
    public ChavePixFactory(Set<ChavePixStrategy> strategySet) {
        createStrategy(strategySet);
    }

    public ChavePixStrategy getStrategy(StrategyEnum... strategyName) {
        return strategies.get(Stream.of(strategyName).findFirst().orElse(StrategyEnum.CELULAR));
    }

    private void createStrategy(Set<ChavePixStrategy> strategySet) {
        strategies = new EnumMap<>(StrategyEnum.class);
        strategySet.forEach(strategy ->strategies.put(strategy.getStrategy(), strategy));
    }

}
