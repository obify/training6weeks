package com.mycompany.orderservice.controller;


import com.mycompany.orderservice.dto.OrderDTO;
import com.mycompany.orderservice.service.OrderService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Slf4j
@RestController
@RequestMapping("/api/v1")
public class OrderController {

    @Autowired
    private OrderService orderService;

    @PostMapping("/orders")
    public ResponseEntity<OrderDTO> placeOrder(@RequestBody OrderDTO orderDTO) {
        log.info("Entering method placeOrder in OrderController");
        orderDTO = orderService.placeOrder(orderDTO);
        ResponseEntity<OrderDTO> responseEntity = new ResponseEntity<>(orderDTO, HttpStatus.CREATED);
        log.info("Entering method placeOrder in OrderController");
        return responseEntity;
    }

    @GetMapping("/orders/users/{userId}")
    public ResponseEntity<List<OrderDTO>> getAllOrders(@PathVariable Long userId){
        log.info("Entering method getAllOrders in OrderController");
        List<OrderDTO> orderDTOList = orderService.getAllOrders(userId);
        ResponseEntity<List<OrderDTO>> responseEntity = new ResponseEntity<>(orderDTOList, HttpStatus.OK);
        log.info("Entering method placeOrder in getAllOrders");
        return responseEntity;
    }
}
