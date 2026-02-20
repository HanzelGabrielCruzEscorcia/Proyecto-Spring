package com.alibou.order.payment;

import com.alibou.order.customer.CustomerResponse;
import com.alibou.order.order.PaymentMethod;

import java.math.BigDecimal;

public record PaymentRequest(

        BigDecimal amount,
        PaymentMethod paymentMethod,
        Integer orderId,
        String orderReference,
        CustomerResponse customer
) {
}
