package com.capgemini.moneymoney.accountservice.service;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.capgemini.moneymoney.accountservice.entity.Account;
import com.capgemini.moneymoney.accountservice.entity.CurrentAccount;
import com.capgemini.moneymoney.accountservice.entity.SavingsAccount;
import com.capgemini.moneymoney.accountservice.repo.AccountRepository;
@Service
public class AccountsServiceImpl implements AccountService {

	@Autowired
	private AccountRepository repository;

	@Override
	public List<Account> getallAccounts() {
		return  repository.findAll();	
	}


	@Override
	public Optional<Account> getAccountById(int accountNumber) {
		return repository.findById(accountNumber);
	}


	@Override
	public void updateSavingsAccount(SavingsAccount accounts) {
		repository.save(accounts);
	}


	@Override
	public void updateCurrentAccount(CurrentAccount accounts) {
		repository.save(accounts);
	}


	@Override
	public void updateBalance(Account accounts) {
		repository.save(accounts);
	}
	
	

}