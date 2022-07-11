package br.com.itau.pixexample.service;

import br.com.itau.pixexample.entity.ChavePix;
import br.com.itau.pixexample.entity.dto.FilterDTO;
import br.com.itau.pixexample.entity.enums.TipoPessoa;
import br.com.itau.pixexample.exception.BusinessException;
import br.com.itau.pixexample.repository.ChavePixRepository;
import br.com.itau.pixexample.repository.QuerySearchRepository;
import lombok.RequiredArgsConstructor;

import javax.persistence.EntityNotFoundException;
import java.time.LocalDateTime;
import java.util.List;
import java.util.UUID;

@RequiredArgsConstructor
public abstract class ChavePixService implements ChavePixStrategy {

    protected final ChavePixRepository repository;
    protected final QuerySearchRepository querySearchRepository;

    @Override
    public UUID create(ChavePix entity) {
        businessValidation(entity);
        maxQuantityValidation(entity);
        duplicateValidation(entity);
        entity.setDataCriacao(LocalDateTime.now());
        repository.save(entity);
        return entity.getId();
    }

    @Override
    public ChavePix update(ChavePix entity) {
        businessValidation(entity);
        maxQuantityValidation(entity);
        duplicateValidation(entity);
        return repository.save(entity);
    }

    @Override
    public ChavePix find(UUID id) {
        return repository
                .findById(id)
                .orElseThrow(EntityNotFoundException::new);
    }

    @Override
    public List<ChavePix> findAllByParams(FilterDTO filter) {
        return throwIfEmpty(querySearchRepository.findAllByFilter(filter));
    }

    @Override
    public ChavePix delete(UUID id) {
        return repository.save(ChavePix
                .builder()
                .id(id)
                .ativo(Boolean.FALSE)
                .dataInativacao(LocalDateTime.now())
                .build());
    }

    private void maxQuantityValidation(ChavePix entity) {
        var count = repository.count(entity.getNumeroAgencia(), entity.getNumeroConta());
        if(entity.getTipoPessoa().equals(TipoPessoa.F) && count == 5) {
            throw new BusinessException("Não pode ultrapassar a quantidade máxima de 5 chaves");
        } else if(count == 20) {
            throw new BusinessException("Não pode ultrapassar a quantidade máxima de 20 chaves");
        }
    }

    private void duplicateValidation(ChavePix entity) {
        var count = repository.countByValorChaveAndTipoChave(entity.getValorChave(), entity.getTipoChave());
        if(count > 0) {
            throw new BusinessException("Chave duplicada");
        }
    }

    private List<ChavePix> throwIfEmpty(List<ChavePix> list) {
        if(list.isEmpty()) {
            throw new EntityNotFoundException("Não Encontrado");
        }
        return list;
    }

    public abstract void businessValidation(ChavePix entity);

}
