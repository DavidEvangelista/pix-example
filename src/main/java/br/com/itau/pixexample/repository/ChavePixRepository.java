package br.com.itau.pixexample.repository;

import br.com.itau.pixexample.entity.ChavePix;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.UUID;

@Repository
public interface ChavePixRepository extends JpaRepository<ChavePix, UUID>, QuerySearchRepository {

    @Query("SELECT count(c) FROM ChavePix c WHERE c.ativo = true AND c.numeroAgencia = ?1 AND c.numeroConta = ?2")
    Long count(Integer numeroAgencia, Integer numeroConta);
    Long countByValorChave(String valorChave);
}
