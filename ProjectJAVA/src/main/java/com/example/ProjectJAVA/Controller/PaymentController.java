package com.example.ProjectJAVA.Controller;

import com.example.ProjectJAVA.Entity.Orders;
import com.example.ProjectJAVA.Entity.Payments;
import com.example.ProjectJAVA.Enums.OrderStatus;
import com.example.ProjectJAVA.Payloads.Request.PaymentRequest;
import com.example.ProjectJAVA.Payloads.ResponseData;
import com.example.ProjectJAVA.Repository.OrderRepository;
import com.example.ProjectJAVA.Repository.PaymentRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.time.LocalDateTime;

@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    PaymentRepository paymentRepository;

    @PostMapping("/fake")
    public ResponseEntity <?> fakePayment(@RequestBody PaymentRequest paymentRequest){
        ResponseData responseData = new ResponseData();
        Orders orders = orderRepository.findById(paymentRequest.getOrder_id())
                .orElseThrow(() -> new RuntimeException("Order not found!" + paymentRequest.getOrder_id()));

        Payments payments = new Payments();
        payments.setOrders(orders);
        payments.setPaymentMethod("Fake Payment");
        payments.setPaymentStatus("Success");
        payments.setDatePaid(LocalDateTime.now());
        paymentRepository.save(payments);
        orders.setStatus(OrderStatus.COMPLETED);
        orderRepository.save(orders);
        responseData.setData(orders);
        return new ResponseEntity<>(responseData, HttpStatus.OK);
    }


}
