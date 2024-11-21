package com.unq.dapp_grupo_e.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unq.dapp_grupo_e.model.Transaction;
import com.unq.dapp_grupo_e.model.TransactionSummary;

import jakarta.transaction.Transactional;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction, Integer>{

    @Modifying
    @Transactional
    @Query(value = "ALTER TABLE transaction AUTO_INCREMENT = 1", nativeQuery = true)
    void resetIdTransaction();

    @Query("SELECT new com.unq.dapp_grupo_e.model.TransactionSummary(t.symbolTrade, SUM(t.cryptoNominalValue)) " + 
           "FROM Transaction t " + 
           "WHERE t.idUser = :idUser AND " + 
           "t.status != 'CANCELLED' AND " +
           "STR_TO_DATE(t.dateTimeCreated, '%d/%m/%Y %H:%i:%s') " + 
           "BETWEEN STR_TO_DATE(:startDate, '%d/%m/%Y %H:%i:%s') AND STR_TO_DATE(:endDate, '%d/%m/%Y %H:%i:%s') " +
           "GROUP BY t.symbolTrade")
    List<TransactionSummary> getTotalNominalValuesOfUserBetweenDates(
        @Param("idUser") Integer idUser, 
        @Param("startDate") String startDate, 
        @Param("endDate") String endDate);
    
    
    @Query("SELECT t " +
           "FROM Transaction t " +
           "WHERE t.status = 'ACTIVE'")
    List<Transaction> findAllActiveTransaction();

}
