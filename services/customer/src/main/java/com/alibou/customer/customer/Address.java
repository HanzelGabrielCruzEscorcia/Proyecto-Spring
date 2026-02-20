package com.alibou.customer.customer;


import lombok.*;

import org.springframework.validation.annotation.Validated;

@Getter
@Builder
@NoArgsConstructor
@AllArgsConstructor
@Setter
@Validated
public class Address {

    private String street;
    private String houseNumber;
    private String zipCode;

}
