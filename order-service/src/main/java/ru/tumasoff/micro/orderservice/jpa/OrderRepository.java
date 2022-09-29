package ru.tumasoff.micro.orderservice.jpa;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.tumasoff.micro.orderservice.domain.Order;

@SuppressWarnings("unused")
@Repository
public interface OrderRepository extends MongoRepository<Order, String> {
  
}
