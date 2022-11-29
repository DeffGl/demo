package com.example.demo.util.errors;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
@AllArgsConstructor
public class ClientErrorResponse {
    private String message;
    private Date timestamp;
}
