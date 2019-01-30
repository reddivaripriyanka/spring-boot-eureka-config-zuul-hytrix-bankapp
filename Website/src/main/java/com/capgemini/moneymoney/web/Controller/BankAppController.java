package com.capgemini.moneymoney.web.Controller;

import static org.springframework.hateoas.mvc.ControllerLinkBuilder.linkTo;
import static org.springframework.hateoas.mvc.ControllerLinkBuilder.methodOn;

import java.util.ArrayList;
import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.Link;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.servlet.ModelAndView;

import com.capgemini.moneymoney.web.entiry.CurrentDataSet;
import com.capgemini.moneymoney.web.entiry.Transaction;
import com.netflix.hystrix.contrib.javanica.annotation.HystrixCommand;



@Controller
public class BankAppController {
	
	@Autowired
	private RestTemplate restTemplate;
	@RequestMapping("/")
	public String indexForm() {
		return "index";
	}
	
	@RequestMapping("/deposit")
	public String depositForm() {
		return "DepositForm";
	}
	@RequestMapping("/depositForm")
	@HystrixCommand(fallbackMethod="depositMethod")
	public String deposit(@ModelAttribute Transaction transaction,
			Model model) {
		System.out.println(transaction);
		restTemplate.postForEntity("http://zuul-proxy/transcation-service/transactions/deposit", 
				transaction, null);
		System.out.println("after post");
		model.addAttribute("message","Success!");
		return "DepositForm";
	}
	public String depositMethod(@ModelAttribute Transaction transaction,
			Model model) {
		String output="if deposit fails it show error";
		model.addAttribute("message", output);
		return "DepositForm";
		
	}
	@RequestMapping("/withdraw")
	public String withdrawForm() {
		return "WithdrawForm";
	}
	@RequestMapping("/withdrawForm")
	@HystrixCommand(fallbackMethod="withdrawMethod")
	public String withdraw(@ModelAttribute Transaction transaction,
			Model model) {
		restTemplate.postForEntity("http://zuul-proxy/transcation-service/transactions/withdraw", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "WithdrawForm";
	}
	public String withdrawMethod(@ModelAttribute Transaction transaction,
			Model model) {
		String output="if withdraw fails it show error";
		model.addAttribute("message", output);
		return "WithdrawForm";
		
	}
	@RequestMapping("/fundtransfer")
	public String fundtransfer() {
		return "FundtransferForm";
	}
	@RequestMapping("/fundtransferForm")
	@HystrixCommand(fallbackMethod="fundTransforMethod")
	public String fundTransfer(@ModelAttribute Transaction transaction,Model model,@RequestParam("SenderAccountNumber") int SenderAccountNumber,@RequestParam("ReceiverAccountNumber") int ReceiverAccountNumber,@RequestParam("amount") double amout) {
		transaction.setAccountNumber(SenderAccountNumber);
		restTemplate.postForEntity("http://zuul-proxy/transcation-service/transactions/withdraw", 
				transaction, null);
		transaction.setAccountNumber(ReceiverAccountNumber);
		restTemplate.postForEntity("http://zuul-proxy/transcation-service/transactions/deposit", 
				transaction, null);
		model.addAttribute("message","Success!");
		return "FundtransferForm";
		
	}
	public String fundTransforMethod(@ModelAttribute Transaction transaction,Model model,@RequestParam("SenderAccountNumber") int SenderAccountNumber,@RequestParam("ReceiverAccountNumber") int ReceiverAccountNumber,@RequestParam("amount") double amout) {
		String output="if fundTransfer fails it show error";
		model.addAttribute("message", output);
		return "FundtransferForm";
		
	}
	
	@RequestMapping("/statementDeposit")
	public ModelAndView getStatementDeposit(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet = restTemplate.getForObject("http://zuul-proxy/transcation-service/transactions/statement", CurrentDataSet.class);
		int currentSize=size==0?5:size;
		int currentOffset=offset==0?1:offset;
		Link next=linkTo(methodOn(BankAppController.class).getStatementDeposit(currentOffset+currentSize,currentSize)).withRel("next");
		Link previous=linkTo(methodOn(BankAppController.class).getStatementDeposit(currentOffset-currentSize, currentSize)).withRel("previous");
		List<Transaction> transactions = currentDataSet.getTransactions();
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) { 
			  if((transactions.size()<=i && i>0) || currentOffset<1) 
				  break;
			Transaction transaction = transactions.get(i);
			currentDataSetList.add(transaction);
			
		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		return new ModelAndView("DepositForm","currentDataSet",dataSet);
	}
	
	@RequestMapping("/statementWithdraw")
	public ModelAndView getStatementWithdraw(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet = restTemplate.getForObject("http://zuul-proxy/transcation-service/transactions/statement", CurrentDataSet.class);
		int currentSize=size==0?5:size;
		int currentOffset=offset==0?1:offset;
		Link next=linkTo(methodOn(BankAppController.class).getStatementDeposit(currentOffset+currentSize,currentSize)).withRel("next");
		Link previous=linkTo(methodOn(BankAppController.class).getStatementDeposit(currentOffset-currentSize, currentSize)).withRel("previous");
		List<Transaction> transactions = currentDataSet.getTransactions();
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) { 
			  if((transactions.size()<=i && i>0) || currentOffset<1) 
				  break;
			Transaction transaction = transactions.get(i);
			currentDataSetList.add(transaction);
			
		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		return new ModelAndView("DepositForm","currentDataSet",dataSet);
	}
	
	@RequestMapping("/statementFundTransfer")
	public ModelAndView getStatementFundTransfer(@RequestParam("offset") int offset, @RequestParam("size") int size) {
		CurrentDataSet currentDataSet = restTemplate.getForObject("http://zuul-proxy/transcation-service/transactions/statement", CurrentDataSet.class);
		int currentSize=size==0?5:size;
		int currentOffset=offset==0?1:offset;
		Link next=linkTo(methodOn(BankAppController.class).getStatementDeposit(currentOffset+currentSize,currentSize)).withRel("next");
		Link previous=linkTo(methodOn(BankAppController.class).getStatementDeposit(currentOffset-currentSize, currentSize)).withRel("previous");
		List<Transaction> transactions = currentDataSet.getTransactions();
		List<Transaction> currentDataSetList = new ArrayList<Transaction>();
		
		for (int i = currentOffset - 1; i < currentSize + currentOffset - 1; i++) { 
			  if((transactions.size()<=i && i>0) || currentOffset<1) 
				  break;
			  
			Transaction transaction = transactions.get(i);
			currentDataSetList.add(transaction);
			
		}
		CurrentDataSet dataSet = new CurrentDataSet(currentDataSetList, next, previous);
		return new ModelAndView("DepositForm","currentDataSet",dataSet);
	}
}
