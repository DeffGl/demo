package com.example.demo.services;

import com.example.demo.models.CashOrder;
import com.example.demo.models.ClientAccount;
import com.example.demo.models.Transaction;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.repositories.OrderRepository;
import com.example.demo.repositories.TransactionRepository;
import com.example.demo.util.*;
import com.example.demo.util.exceptions.AccountNotFoundException;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.Date;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class OrderService {

    private final OrderRepository orderRepository;
    private final AccountRepository accountRepository;
    private final TransactionRepository transactionRepository;
    private final Transfer transfer;

    private ClientAccount findAccount(int id) {
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }

    @Transactional
    public ResultCreateOrder createOrder(CashOrder order) {
        order.setDateOfCreate(new Date());
        ResultCreateOrder result = create(order);

        createAndSaveTransaction(order);
        orderRepository.save(order);
        return result;
    }

    private void createAndSaveTransaction(CashOrder order) {
        Transaction transaction = createTransaction(order);
        transactionRepository.save(transaction);
    }

    private Transaction createTransaction(CashOrder order) {
        return new Transaction()
                .setDateOfCreate(order.getDateOfCreate())
                .setSum(order.getSum())
                .setType(order.getType())
                .setAccount(order.getAccount())
                .setOrder(order)
                .setResult(order.isResult());
    }

    private ResultCreateOrder create(CashOrder order){
        try {
            order.setResult(transfer.updateSumOfAccountByOrder(findAccount(order.getAccount().getId()), order));
            return new ResultCreateOrder(order);
        }catch (Exception e){
            return new ResultCreateOrder(order, e);
        }
    }

}
