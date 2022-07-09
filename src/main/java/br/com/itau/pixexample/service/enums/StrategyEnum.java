package br.com.itau.pixexample.service.enums;

import lombok.RequiredArgsConstructor;

import java.util.stream.Stream;

@RequiredArgsConstructor
public enum StrategyEnum {
    CELULAR("CELULAR"),
    EMAIL("EMAIL"),
    CPF_CNPJ("CPF_CNPJ"),
    ALEATORIO("ALEATORIO");

    private final String strategyName;

    public static StrategyEnum fromStrategyName(String value) {
        return Stream.of(StrategyEnum.values())
                .filter(s -> s.strategyName.contains(value))
                .findFirst()
                .orElse(StrategyEnum.CELULAR);
    }
}
