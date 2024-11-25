package com.unq.dapp_grupo_e.repository;

import java.util.List;

import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.unq.dapp_grupo_e.model.CryptoCurrency;

import jakarta.transaction.Transactional;

@Repository
public interface CryptoCurrencyRepository extends CrudRepository<CryptoCurrency, String> { 


       @Query("SELECT c " + 
              "FROM CryptoCurrency c " + 
              "WHERE c.symbol = :symbol AND " + 
              "STR_TO_DATE(c.lastUpdateDateAndTime, '%d/%m/%Y %H:%i:%s') " + 
              "BETWEEN STR_TO_DATE(:startDate, '%d/%m/%Y %H:%i:%s') AND STR_TO_DATE(:endDate, '%d/%m/%Y %H:%i:%s') ")
       List<CryptoCurrency> getLatestCotizationsOf(
           @Param("symbol") String symbol, 
           @Param("startDate") String startDate, 
           @Param("endDate") String endDate);
       
       @Modifying
       @Transactional 
       @Query("DELETE " +
              "FROM CryptoCurrency c " +
              "WHERE c.symbol = :symbol")
       void deleteCryptoPrices(@Param("symbol") String symbol);
 }
