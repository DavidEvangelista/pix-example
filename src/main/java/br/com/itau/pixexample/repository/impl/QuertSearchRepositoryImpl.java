package br.com.itau.pixexample.repository.impl;

import br.com.itau.pixexample.entity.ChavePix;
import br.com.itau.pixexample.entity.dto.FilterDTO;
import br.com.itau.pixexample.repository.QuerySearchRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuertSearchRepositoryImpl implements QuerySearchRepository {

    @PersistenceContext
    private EntityManager em;

    @Override
    public List<ChavePix> findAllByFilter(FilterDTO filter) {
        var criteriaBuilder = em.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(ChavePix.class);
        var chavePixRoot = criteriaQuery.from(ChavePix.class);
        var predicates = new ArrayList<Predicate>();

        if (filter.getAgencia() != null && filter.getConta() != null) {
            predicates.add(criteriaBuilder.equal(chavePixRoot.get("numeroAgencia"), filter.getAgencia()));
            predicates.add(criteriaBuilder.equal(chavePixRoot.get("numeroConta"), filter.getConta()));
        }
        if (filter.getCorrentista() != null) {
            predicates.add(criteriaBuilder.equal(chavePixRoot.get("nomeCorrentista"), filter.getCorrentista()));
        }
        if (filter.getTipoChave() != null) {
            predicates.add(criteriaBuilder.equal(chavePixRoot.get("tipoChave"), filter.getTipoChave()));
        }
        if (filter.getDataCriacao() != null) {
            predicates.add(criteriaBuilder.equal(chavePixRoot.get("dataCriacao"), filter.getDataCriacao()));
        } else if(filter.getDataInativacao() != null) {
            predicates.add(criteriaBuilder.equal(chavePixRoot.get("dataInativaca"), filter.getDataInativacao()));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(criteriaQuery).getResultList();
    }
}
