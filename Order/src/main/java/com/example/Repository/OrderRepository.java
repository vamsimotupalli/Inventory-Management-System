package com.example.Repository;

import com.example.CommonDTO.Orders;
import org.springframework.data.jpa.repository.JpaRepository;

public interface OrderRepository extends JpaRepository<Orders, Integer> {

}
