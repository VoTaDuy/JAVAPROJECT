package com.example.ProjectJAVA.Controller;


import com.example.ProjectJAVA.DTO.OrderDTO;
import com.example.ProjectJAVA.Entity.Orders;
import com.example.ProjectJAVA.Exception.ResourceNotFoundException;
import com.example.ProjectJAVA.Payloads.ResponseData;
import com.example.ProjectJAVA.Service.Imp.OrderServiceImp;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@CrossOrigin("*")
@RestController
@RequestMapping("/order")
public class OrderController {


    @Autowired
    OrderServiceImp orderServiceImp;

    @PostMapping("/create")
    public ResponseEntity<?> createOrder(@RequestParam Integer userId){

        ResponseData responseData = new ResponseData();

        try {
            Orders orders = orderServiceImp.placeOrder(userId);
            responseData.setData(orders);
            responseData.setDesc("Item Ordered Successfully!");
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        }catch (Exception e){
            responseData.setDesc(e.getMessage());
            return new ResponseEntity<>(responseData, HttpStatus.INTERNAL_SERVER_ERROR);

        }
    }

    @GetMapping("/get/{orderId}")
    public ResponseEntity<?> getOrderById(@PathVariable Integer orderId){
        ResponseData responseData = new ResponseData();
        try{
            OrderDTO order = orderServiceImp.getOrder(orderId);
            responseData.setData(order);
            return new ResponseEntity<>(responseData, HttpStatus.OK);
        } catch (ResourceNotFoundException e) {
            return new ResponseEntity<>(e.getMessage(), HttpStatus.NOT_FOUND);
        }
    }


    @GetMapping("/{userId}/get")
    public  ResponseEntity<?> getUserOrder(@PathVariable Integer userId){

        try {
            List<OrderDTO> orderDTOList = orderServiceImp.getUserOrders(userId);
            return new ResponseEntity<>(orderDTOList, HttpStatus.OK);

        }catch (Exception e){
            return new ResponseEntity<>(e.getMessage(), HttpStatus.INTERNAL_SERVER_ERROR);
        }
    }

}
