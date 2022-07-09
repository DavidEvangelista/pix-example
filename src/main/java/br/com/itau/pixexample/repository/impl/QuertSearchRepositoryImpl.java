package br.com.itau.pixexample.repository.impl;

import br.com.itau.pixexample.entity.ChavePix;
import br.com.itau.pixexample.entity.dto.SearchDTO;
import br.com.itau.pixexample.repository.QuerySearchRepository;
import org.springframework.stereotype.Repository;

import javax.persistence.EntityManager;
import javax.persistence.criteria.Predicate;
import java.util.ArrayList;
import java.util.List;

@Repository
public class QuertSearchRepositoryImpl implements QuerySearchRepository {

    EntityManager em;

    @Override
    public List<ChavePix> findAllByFilter(SearchDTO search) {
        var criteriaBuilder = em.getCriteriaBuilder();
        var criteriaQuery = criteriaBuilder.createQuery(ChavePix.class);
        var chavePixRoot = criteriaQuery.from(ChavePix.class);
        var predicates = new ArrayList<Predicate>();

        if (search.getAgencia() != null && search.getConta() != null) {
            predicates.add(criteriaBuilder.equal(chavePixRoot.get("agencia"), search.getAgencia()));
            predicates.add(criteriaBuilder.equal(chavePixRoot.get("conta"), search.getConta()));
        }
        if (search.getCorrentista() != null) {
            predicates.add(criteriaBuilder.equal(chavePixRoot.get("correntista"), search.getCorrentista()));
        }
        if (search.getTipoChave() != null) {
            predicates.add(criteriaBuilder.equal(chavePixRoot.get("tipoChave"), search.getTipoChave()));
        }
        if (search.getDataCriacao() != null) {
            predicates.add(criteriaBuilder.equal(chavePixRoot.get("dataCriacao"), search.getDataCriacao()));
        } else if(search.getDataInativaca() != null) {
            predicates.add(criteriaBuilder.equal(chavePixRoot.get("dataInativaca"), search.getDataInativaca()));
        }
        criteriaQuery.where(predicates.toArray(new Predicate[0]));
        return em.createQuery(criteriaQuery).getResultList();
    }
}
