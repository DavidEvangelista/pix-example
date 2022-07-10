package br.com.itau.pixexample.repository;

import br.com.itau.pixexample.entity.ChavePix;
import br.com.itau.pixexample.entity.dto.FilterDTO;

import java.util.List;

public interface QuerySearchRepository {

    List<ChavePix> findAllByFilter(FilterDTO filter);

}
