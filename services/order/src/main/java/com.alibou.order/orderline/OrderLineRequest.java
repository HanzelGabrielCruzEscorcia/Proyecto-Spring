package com.alibou.order.orderline;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public record OrderLineRequest(

        Integer id,

        Integer OrderId,

        Integer productId,

        double quantity) {
}
