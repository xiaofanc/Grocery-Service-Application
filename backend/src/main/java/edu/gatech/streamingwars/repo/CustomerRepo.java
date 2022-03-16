package edu.gatech.streamingwars.repo;

import edu.gatech.streamingwars.model.Customer;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface CustomerRepo extends JpaRepository<Customer, String> {

    Optional<Customer> findCustomerByAccount(String account);
}
