package com.example.gallery.backend.controller;

import java.util.List;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.server.ResponseStatusException;

import com.example.gallery.backend.dto.OrderDto;
import com.example.gallery.backend.entity.Order;
import com.example.gallery.backend.repository.CartRepository;
import com.example.gallery.backend.repository.OrderRepository;
import com.example.gallery.backend.service.JwtService;

@RestController
public class OrderController {

    @Autowired
    OrderRepository orderRepository;
    
    @Autowired
    JwtService jwtService;

    @Autowired
    CartRepository cartRepository;

    @GetMapping("/api/orders")
    public ResponseEntity<List<Order>> getOrders(@CookieValue(value = "token", required = false) String token){

        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }
        
        int meberId = jwtService.getId(token);

        List<Order> orders = orderRepository.findByMemberIdOrderByIdDesc(meberId);

        return new ResponseEntity<>(orders,HttpStatus.OK);

    }

    @Transactional
    @PostMapping("/api/orders")
    public ResponseEntity<Order> pushOrder(@RequestBody OrderDto dto, @CookieValue(value = "token", required = false) String token){

        if(!jwtService.isValid(token)){
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED);
        }

        int meberId = jwtService.getId(token);
        Order newOrder = new Order();

        newOrder.setMemberId(meberId);
        newOrder.setName(dto.getName());
        newOrder.setAddress(dto.getAddress());
        newOrder.setPayment(dto.getPayment());
        newOrder.setCardNumber(dto.getCardNumber());
        newOrder.setItems(dto.getItems());

        orderRepository.save(newOrder);
        cartRepository.deleteByMemberId(meberId);

        return new ResponseEntity<>(HttpStatus.OK);
        
    }

}
