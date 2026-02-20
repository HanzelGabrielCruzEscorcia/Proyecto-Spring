package com.alibou.customer.handler;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;

import java.util.Map;

public record ErrorResponse (
    Map<String, String> errors
){

}
