package com.capgemini.moneymoney.accountservice.resource;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.capgemini.moneymoney.accountservice.entity.Account;
import com.capgemini.moneymoney.accountservice.service.AccountService;

@RestController
@RequestMapping("/accounts")
public class AccountResource {
	
	@Autowired
	private AccountService service;

	@GetMapping
	public ResponseEntity<List<Account>> getAllAccounts(){
		List<Account> accounts=service.getallAccounts();
		return new ResponseEntity<>(accounts, HttpStatus.OK);
	}
	@GetMapping("/{accountNumber}")
	public ResponseEntity<Account> getAccountbyId(@PathVariable int accountNumber){
		Optional<Account> optional = service.getAccountById(accountNumber);
		Account account = optional.get();
		return new ResponseEntity<Account>(account, HttpStatus.OK);
	}

	@GetMapping("/{accountNumber}/balance")
	public ResponseEntity<Double> getCurrentBalance(@PathVariable int accountNumber){
		Optional<Account> optional = service.getAccountById(accountNumber);
		double currentBalance = optional.get().getCurrentBalance();
		if(optional.get()==null) {
			return new ResponseEntity<>(null,HttpStatus.NOT_FOUND);
		}
		return new ResponseEntity<>(currentBalance,HttpStatus.OK);
	}
	@PutMapping("/{accountNumber}")
	public ResponseEntity<Account> updateAccountBalance(@PathVariable int accountNumber,@RequestParam("currentBalance")Double currentBalance){
		Optional<Account> optional = service.getAccountById(accountNumber);
		Account accounts =  optional.get();
		if(accounts==null) {
			return new ResponseEntity<>(null, HttpStatus.NOT_FOUND);
		}
		accounts.setCurrentBalance(currentBalance);
		service.updateBalance(accounts);
		return new ResponseEntity<Account>(accounts, HttpStatus.OK);
		
	}
}