package com.ar.bankingonline.infrastructure.repositories;

import com.ar.bankingonline.domain.models.Account;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface AccountRepository extends JpaRepository<Account,Long> {

    // TODO: Agregar una busqueda por numero de cuenta en la BD

    //@Query(nativeQuery = true,value = "SELECT * FROM account WHERE nombre = :number")
    //Account getAccountByNumber(@Param("number") int number);


}
