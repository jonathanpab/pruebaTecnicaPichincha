package com.example.demo.repository;

import com.example.demo.entity.AccountClient;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

public interface AccountClientRepository extends JpaRepository<AccountClient, Long> {

    @Query("FROM AccountClient WHERE numero_cuenta = :accountNumber")
    AccountClient findByAccountNumber(@Param("accountNumber") long accountNumber);
}
