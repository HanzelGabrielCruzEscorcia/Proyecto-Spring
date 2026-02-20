package com.alibou.order.kafka;

import com.alibou.order.customer.CustomerResponse;
import com.alibou.order.order.PaymentMethod;
import com.alibou.order.product.PurchaseResponse;

import java.math.BigDecimal;
import java.util.List;

public record OrderConfirmation(
        String orderReference,
        BigDecimal totalAmount,
        PaymentMethod paymentMethod,
        CustomerResponse customer,
        List<PurchaseResponse> products
) {

}
