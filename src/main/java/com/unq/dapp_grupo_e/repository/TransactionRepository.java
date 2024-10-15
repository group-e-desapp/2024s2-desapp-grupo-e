package com.unq.dapp_grupo_e.repository;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.unq.dapp_grupo_e.model.Transaction;

import jakarta.transaction.Transactional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE transaction AUTO_INCREMENT = 1", nativeQuery = true)
    void resetIdTransaction();
    
}
