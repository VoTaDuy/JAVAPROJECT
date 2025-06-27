package com.example.ProjectJAVA.Service.Imp;

import com.example.ProjectJAVA.DTO.OrderDTO;
import com.example.ProjectJAVA.Entity.Orders;

import java.util.List;

public interface OrderServiceImp {
    Orders placeOrder(Integer userId);

    OrderDTO getOrder(Integer orderId);

    List<OrderDTO> getAllOrders();

    public List<OrderDTO> getUserOrders(Integer userId);
}
