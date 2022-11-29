package com.example.demo.util;

import com.example.demo.models.CashOrder;
import com.example.demo.models.Client;
import com.example.demo.models.ClientAccount;
import com.example.demo.models.Transaction;
import com.example.demo.repositories.AccountRepository;
import com.example.demo.util.exceptions.AccountNotFoundException;
import com.example.demo.util.exceptions.IncorrectSecretWordException;
import com.example.demo.util.exceptions.InsufficientSumException;
import lombok.RequiredArgsConstructor;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class Transfer {

    private final AccountRepository accountRepository;

    public boolean updateSumOfAccountByOrder(ClientAccount account, CashOrder order) {
        double sum = order.getSum();
        boolean result = false;
        switch (order.getType()) {
            case ("Пополнение"):
                result = isReplenishForAccount(account, sum);
                break;
            case ("Снятие"):
                result = isDeductFromAccount(account, order.getAccount().getClient().getSecretWord(), sum);
                break;
        }
        return result;
    }

    public boolean updateSumOfAccountByTransaction(ClientAccount account, ClientAccount outgoingAccountFromDB, Transaction transaction){
        double sum = transaction.getSum();
        return isDeductFromAccount(outgoingAccountFromDB, transaction.getOutgoingAccount().getClient().getSecretWord(), sum) && isReplenishForAccount(account, sum);
    }



    public boolean isReplenishForAccount(ClientAccount account, double sum) {
        account.setSum(account.getSum() + sum);
        return true;
    }

    public boolean isDeductFromAccount(ClientAccount account, String secretWord, double sum) {
        isValidSecretWord(account, secretWord);
        account.setSum(checkResultSum(account, sum));
        return true;
    }

    public double checkResultSum(ClientAccount account, double sum){
        double resultSum = account.getSum() - sum;
        if (resultSum<0){
            throw new InsufficientSumException();
        }
        return resultSum;
    }

    public void isValidSecretWord(ClientAccount account, String secretWord) {
        PasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
        if (!passwordEncoder.matches(secretWord, account.getClient().getSecretWord())){
            throw new IncorrectSecretWordException();
        }
    }

    public ClientAccount findAccount(int id){
        return accountRepository.findById(id).orElseThrow(AccountNotFoundException::new);
    }
}
