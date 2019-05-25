package ar.com.cac.deposito.remoto.repository;

import ar.com.cac.deposito.remoto.domain.Banco;
import org.springframework.data.jpa.repository.*;
import org.springframework.stereotype.Repository;


/**
 * Spring Data  repository for the Banco entity.
 */
@SuppressWarnings("unused")
@Repository
public interface BancoRepository extends JpaRepository<Banco, Long> {

}
