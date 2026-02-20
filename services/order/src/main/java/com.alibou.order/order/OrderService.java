package com.alibou.order.order;

import com.alibou.order.customer.CustomerClient;
import com.alibou.order.exception.BusinessException;
import com.alibou.order.kafka.OrderConfirmation;
import com.alibou.order.kafka.OrderProducer;

import com.alibou.order.orderline.OrderLineRequest;
import com.alibou.order.orderline.OrderLineService;
import com.alibou.order.payment.PaymentClient;
import com.alibou.order.payment.PaymentRequest;
import com.alibou.order.product.ProductClient;
import com.alibou.order.product.PurchaseRequest;
import jakarta.persistence.EntityNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.stream.Collectors;


@Service
@RequiredArgsConstructor
public class OrderService {
    private final OrderRepository repository;
    private final CustomerClient customerClient;
    private final OrderMapper mapper;
    private final ProductClient productClient;
    private final OrderLineService orderLineService;
    private final OrderProducer orderProducer;
    private final PaymentClient paymentClient;

    public Integer createdOrder(@Valid OrderRequest request) {
        var customer = this.customerClient.findCustomerById((request.customerId()))
                .orElseThrow(() -> new BusinessException("Cannot create order:: No customer exists with the provided ID"));

        var purchasedProducts = this.productClient.purchaseProducts(request.products());

        var order = this.repository.save(mapper.toOrder(request));
        for(PurchaseRequest purchaseRequest: request.products()){
         orderLineService.saveOrderLine(
                 new OrderLineRequest(
                         null,
                         order.getId(),
                         purchaseRequest.productId(),
                         purchaseRequest.quantity()
                 )
         );

        }


        var paymentRequest = new PaymentRequest(
                request.amount(),
                request.paymentMethod(),
                order.getId(),
                order.getReference(),
                customer
        );
        paymentClient.requestOrderPayment(paymentRequest);


        orderProducer.sendOrderConfirmation(
          new OrderConfirmation(
                  request.reference(),
                  request.amount(),
                  request.paymentMethod(),
                  customer,
                  purchasedProducts
          )
        );
        return order.getId();
    }

    public List<OrderResponse> findAll() {

        return repository.findAll()
                .stream()
                .map(mapper::fromOrder)
                .collect(Collectors.toList());
    }

    public OrderResponse findById(Integer orderId) {
        return repository.findById(orderId)
                .map(mapper::fromOrder)
                .orElseThrow(() -> new EntityNotFoundException(String.format("No order found with the provided ID: %d", orderId)));

    }
}
