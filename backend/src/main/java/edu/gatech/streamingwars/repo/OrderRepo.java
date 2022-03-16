package edu.gatech.streamingwars.repo;

import edu.gatech.streamingwars.model.Order;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface OrderRepo extends JpaRepository<Order, Long> {

    Optional<Order> findByCustomerAccount(String CustomerAccount);

}
