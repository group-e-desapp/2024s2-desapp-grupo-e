package com.unq.dapp_grupo_e.repository;

import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.unq.dapp_grupo_e.model.Transaction;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>{
    
}
