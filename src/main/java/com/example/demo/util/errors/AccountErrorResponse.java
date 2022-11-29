package com.example.demo.util.errors;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class AccountErrorResponse {
    private String message;
    private Date timestamp;
}
