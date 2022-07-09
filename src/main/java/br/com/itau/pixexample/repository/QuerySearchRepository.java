package br.com.itau.pixexample.repository;

import br.com.itau.pixexample.entity.ChavePix;
import br.com.itau.pixexample.entity.dto.SearchDTO;

import java.util.List;

public interface QuerySearchRepository {

    List<ChavePix> findAllByFilter(SearchDTO search);

}
