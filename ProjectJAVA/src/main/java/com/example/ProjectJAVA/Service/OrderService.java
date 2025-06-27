package com.example.ProjectJAVA.Service;

import com.example.ProjectJAVA.DTO.OrderDTO;
import com.example.ProjectJAVA.Entity.Carts;
import com.example.ProjectJAVA.Entity.OrderItems;
import com.example.ProjectJAVA.Entity.Orders;
import com.example.ProjectJAVA.Entity.Products;
import com.example.ProjectJAVA.Enums.OrderStatus;
import com.example.ProjectJAVA.Exception.ResourceNotFoundException;
import com.example.ProjectJAVA.Repository.OrderRepository;
import com.example.ProjectJAVA.Repository.ProductRepository;
import com.example.ProjectJAVA.Repository.UserRepository;
import com.example.ProjectJAVA.Service.Imp.CartServiceImp;
import com.example.ProjectJAVA.Service.Imp.OrderServiceImp;
import org.aspectj.weaver.ast.Or;
import org.modelmapper.ModelMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;


@Service
public class OrderService implements OrderServiceImp {


    @Autowired
    ProductRepository productRepository;

    @Autowired
    OrderRepository orderRepository;

    @Autowired
    UserRepository userRepository;
    @Autowired
    CartServiceImp cartServiceImp;

    @Autowired
    private ModelMapper modelMapper;

    @Override
    public Orders placeOrder(Integer userId) {
        Carts carts = cartServiceImp.getCartByUserId(userId);
        Orders orders = createOrder(carts);

        List<OrderItems> orderItemsList = createOrderItem(orders, carts);
        orders.setUsers(userRepository.findById(userId).orElseThrow(()-> new RuntimeException("User not found" + userId)));
        orders.setOrderItemsSet(new HashSet<>(orderItemsList));
        orders.setTotalAmount(calculateTotalAmount(orderItemsList));
        Orders savedOrder = orderRepository.save(orders);

        cartServiceImp.ClearCart(carts.getId());

        return savedOrder;
    }

    private Orders createOrder(Carts carts){
        Orders orders = new Orders();
        orders.setStatus(OrderStatus.PENDING);
        orders.setOrderDate(LocalDate.now());
        return orders;
    }
    @Override
    public OrderDTO getOrder(Integer orderId) {
        return orderRepository.findById(orderId)
                .map(this::convertToDTO)
                .orElseThrow(() -> new ResourceNotFoundException("Order not found with ID: " + orderId));
    }

    @Override
    public List<OrderDTO> getAllOrders() {
        List<Orders> ordersList = orderRepository.findAll();
        List<OrderDTO> orderDTOList = new ArrayList<>();
        for (Orders orders : ordersList){
            OrderDTO orderDTO = new OrderDTO();
            orderDTO.setOrderId(orders.getOrderId());
            orderDTO.setUsername(orders.getUsers().getUsername());
            orderDTO.setOrderTime(orders.getOrderDate());
            orderDTO.setStatus(orders.getStatus());
            orderDTO.setTotalAmount(orders.getTotalAmount());
            orderDTOList.add(orderDTO);
        }
        return orderDTOList;
    }

    private List<OrderItems> createOrderItem(Orders orders, Carts carts){
        return carts.getCartItems()
                .stream()
                .map( cartItems -> {
                    Products products = cartItems.getProducts();
                    products.setQuantity(products.getQuantity() - cartItems.getQuantity());
                    productRepository.save(products);
                    return new OrderItems(
                            cartItems.getQuantity(),
                            cartItems.getUnitPrice(),
                            products,
                            orders);
                }).toList();
    }
    private BigDecimal calculateTotalAmount(List<OrderItems> orderItemsList){
        return orderItemsList
                .stream()
                .map(item -> item.getPrice()
                        .multiply(new BigDecimal(item.getQuantity()))).reduce(BigDecimal.ZERO, BigDecimal::add);

    };


    @Override
    public List<OrderDTO> getUserOrders(Integer userId) {

        List<Orders> ordersList = orderRepository.findByUsers_id(userId);
        return ordersList.stream().map(this::convertToDTO).toList();

    }

    private OrderDTO convertToDTO(Orders order) {
        return modelMapper.map(order, OrderDTO.class);
    }
}
