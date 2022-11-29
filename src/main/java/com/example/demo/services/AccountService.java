package com.example.demo.services;


import com.example.demo.models.CashOrder;
import com.example.demo.models.ClientAccount;
import com.example.demo.models.Transaction;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.util.exceptions.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;


import java.util.List;


@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class AccountService {

    private final AccountRepository accountRepository;

    public List<Transaction> getTransactions(int id){
        ClientAccount account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        return account.getTransactions();
    }

    public List<CashOrder> getOrders(int id){
        ClientAccount account = accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
        return account.getOrder();
    }
}
