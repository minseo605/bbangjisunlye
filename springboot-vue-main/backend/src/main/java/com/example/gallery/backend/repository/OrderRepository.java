package com.example.gallery.backend.repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.example.gallery.backend.entity.Order;

public interface OrderRepository extends JpaRepository<Order, Integer>{

    List<Order> findByMemberIdOrderByIdDesc(int memberId);
}
