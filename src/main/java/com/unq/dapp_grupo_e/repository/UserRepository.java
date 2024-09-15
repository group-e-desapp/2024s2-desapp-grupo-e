package com.unq.dapp_grupo_e.repository;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import com.unq.dapp_grupo_e.model.User;

@Repository
public interface UserRepository extends CrudRepository<User, Integer> {
    
}
