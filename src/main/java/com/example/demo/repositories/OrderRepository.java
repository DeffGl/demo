package com.example.demo.repositories;

import com.example.demo.models.CashOrder;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<CashOrder, Integer> {
}
