package ru.tumasoff.micro.customerservice.jpa;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.stereotype.Repository;
import ru.tumasoff.micro.customerservice.domain.Customer;


@SuppressWarnings("unused")
@Repository
public interface CustomerRepository extends MongoRepository<Customer, String> {

}
