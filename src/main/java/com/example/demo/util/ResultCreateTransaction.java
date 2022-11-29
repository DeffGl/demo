package com.example.demo.util;

import com.example.demo.models.CashOrder;
import com.example.demo.models.Transaction;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@AllArgsConstructor
public class ResultCreateTransaction {
    private Transaction transaction;
    private Exception exception;

    public ResultCreateTransaction(Transaction transaction) {
        this.transaction = transaction;
    }
}
