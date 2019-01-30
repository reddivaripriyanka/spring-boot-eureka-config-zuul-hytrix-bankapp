package com.capgemini.moneymoney.accountservice.repo;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;

import com.capgemini.moneymoney.accountservice.entity.Account;



@Repository
public interface AccountRepository  extends MongoRepository<Account, Object> { 

}