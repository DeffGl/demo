package com.example.demo.services;

import com.example.demo.models.CashOrder;
import com.example.demo.models.ClientAccount;
import com.example.demo.models.Transaction;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.TransactionRepository;
import com.example.demo.util.ResultCreateTransaction;
import com.example.demo.util.Transfer;
import com.example.demo.util.exceptions.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;
import java.util.List;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TransactionService {

    private final TransactionRepository transactionRepository;
    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final Transfer transfer;

    @Transactional
    public ResultCreateTransaction createTransaction(Transaction transaction){
        transaction.setDateOfCreate(new Date());
        ResultCreateTransaction result = create(transaction);

        transactionRepository.save(transaction);
        return result;
    }

    private ResultCreateTransaction create(Transaction transaction){
        try{
            transaction.setResult(transfer.updateSumOfAccountByTransaction(
                    transfer.findAccount(transaction.getAccount().getId()),
                    transfer.findAccount(transaction.getOutgoingAccount().getId()),
                    transaction));
            return new ResultCreateTransaction(transaction);
        }catch (Exception e){
            return new ResultCreateTransaction(transaction, e);
        }
    }

}
