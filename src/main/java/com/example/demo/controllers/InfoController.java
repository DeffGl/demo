package com.example.demo.controllers;

import com.example.demo.models.CashOrder;
import com.example.demo.models.Client;
import com.example.demo.models.ClientAccount;
import com.example.demo.models.Transaction;
import com.example.demo.services.AccountService;
import com.example.demo.services.ClientService;
import com.example.demo.services.OrderService;
import com.example.demo.services.TransactionService;
import com.example.demo.util.*;
import com.example.demo.util.errors.AccountErrorResponse;
import com.example.demo.util.errors.ClientErrorResponse;
import com.example.demo.util.errors.InsufficientSumErrorResponse;
import com.example.demo.util.errors.SecretWordErrorResponse;
import com.example.demo.util.exceptions.AccountNotFoundException;
import com.example.demo.util.exceptions.ClientNotFoundException;
import com.example.demo.util.exceptions.IncorrectSecretWordException;
import com.example.demo.util.exceptions.InsufficientSumException;
import lombok.RequiredArgsConstructor;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@RestController
@RequiredArgsConstructor
@RequestMapping
public class InfoController {

    private final ClientService clientService;
    private final AccountService accountService;
    private final OrderService orderService;
    private final TransactionService transactionService;

    @GetMapping("/clients")
    public List<Client> getAllClients(){
        return clientService.getAll();
    }

    @GetMapping("/client")
    public Client getClient(@RequestParam("id") int id){
        return clientService.getClient(id);
    }

    @ExceptionHandler
    private ResponseEntity<ClientErrorResponse> handlerException(ClientNotFoundException e){
        ClientErrorResponse response = new ClientErrorResponse("Client with this id wasn't found.", new Date());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @GetMapping("/accounts")
    public List<ClientAccount> getAccounts(@RequestParam("id") int id){
        return clientService.getAccounts(id);
    }

    @GetMapping("/transactions")
    public List<Transaction> getTransactions(@RequestParam("id") int id){
        return accountService.getTransactions(id);
    }

    @GetMapping("/orders")
    public List<CashOrder> getOrders(@RequestParam("id") int id){
        return accountService.getOrders(id);
    }

    @PostMapping("/order")
    public void createOrder(@RequestBody CashOrder order) throws Exception {
        ResultCreateOrder resultCreateOrder = orderService.createOrder(order);
        if (!resultCreateOrder.getOrder().isResult()){
            throw resultCreateOrder.getException();
        }
    }

    @ExceptionHandler
    private ResponseEntity<InsufficientSumErrorResponse> handlerException(InsufficientSumException e){
        InsufficientSumErrorResponse response = new InsufficientSumErrorResponse("Insufficient funds in the account.", new Date());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<SecretWordErrorResponse> handlerException(IncorrectSecretWordException e){
        SecretWordErrorResponse response = new SecretWordErrorResponse("The secret word is incorrect.", new Date());
        return new ResponseEntity<>(response, HttpStatus.BAD_REQUEST);
    }

    @ExceptionHandler
    private ResponseEntity<AccountErrorResponse> handlerException(AccountNotFoundException e){
        AccountErrorResponse response = new AccountErrorResponse("Account with this id wasn't found.", new Date());
        return new ResponseEntity<>(response, HttpStatus.NOT_FOUND);
    }

    @PostMapping("/transfer")
    public void transferMoney(@RequestBody Transaction transaction) throws Exception {
        ResultCreateTransaction resultCreateTransaction = transactionService.createTransaction(transaction);
        if (!resultCreateTransaction.getTransaction().isResult()){
            throw resultCreateTransaction.getException();
        }
    }







}




 /*@PostMapping("/register")
    public void register(@RequestBody Client client){
        clientService.createClient(client);
    }

    @PostMapping("/test")
    public boolean test(@RequestBody Client client){
        List<Client> clients = clientService.getAll();
        if (passwordEncoder.matches(client.getSecretWord(), clients.get(client.getId()-1).getSecretWord())){
            System.out.println("done");
            return true;
        }
        else {
            System.out.println("error");
            return false;
        }
    }*/