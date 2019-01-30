package com.capgemini.moneymoney.accountservice.service;

import java.util.List;
import java.util.Optional;

import com.capgemini.moneymoney.accountservice.entity.Account;
import com.capgemini.moneymoney.accountservice.entity.CurrentAccount;
import com.capgemini.moneymoney.accountservice.entity.SavingsAccount;

public interface AccountService {

	List<Account> getallAccounts();

	Optional<Account> getAccountById(int accountNumber);

	void updateSavingsAccount(SavingsAccount accounts);

	void updateCurrentAccount(CurrentAccount accounts);

	void updateBalance(Account accounts);

	


}
