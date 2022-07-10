package br.com.itau.pixexample.service;

import br.com.itau.pixexample.entity.ChavePix;
import br.com.itau.pixexample.entity.dto.FilterDTO;
import br.com.itau.pixexample.service.enums.StrategyEnum;

import java.util.List;
import java.util.UUID;

public interface ChavePixStrategy {

    UUID create(ChavePix dto);

    ChavePix update(ChavePix dto);

    ChavePix delete(UUID id);

    ChavePix find(UUID id);

    List<ChavePix> findAllByParams(FilterDTO filter);

    StrategyEnum getStrategy();

}
