package com.alibou.customer.customer;

import jakarta.validation.constraints.NotNull;

public record CustomerRequest(

         String id,
         @NotNull(message = "Customer firstname is required")

         String firstname,
         @NotNull(message = "Customer lastname is required")
         String lastname,
         @NotNull(message = "Customer email is not a valid email address")
         String email,

         Address adress
) {
}
