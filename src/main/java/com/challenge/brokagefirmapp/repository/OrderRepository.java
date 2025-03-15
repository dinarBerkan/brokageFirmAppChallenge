package com.challenge.brokagefirmapp.repository;

import com.challenge.brokagefirmapp.domain.Order;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.Date;
import java.util.List;

@Repository
public interface OrderRepository extends CrudRepository<Order, Long> {
    List<Order> findByCustomerIdAndCreateDateBetween(Long customerId, Date createDateAfter, Date createDateBefore);
}
