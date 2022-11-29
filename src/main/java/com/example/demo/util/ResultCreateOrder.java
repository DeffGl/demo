package com.example.demo.util;

import com.example.demo.models.CashOrder;
import lombok.*;

@Getter
@Setter
@AllArgsConstructor
public class ResultCreateOrder {
    private CashOrder order;
    private Exception exception;

    public ResultCreateOrder(CashOrder order) {
        this.order = order;
    }
}
