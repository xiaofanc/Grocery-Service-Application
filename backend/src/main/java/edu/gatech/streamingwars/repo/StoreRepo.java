package edu.gatech.streamingwars.repo;

import edu.gatech.streamingwars.model.Store;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface StoreRepo extends JpaRepository<Store, String> {

    Optional<Store> findStoreByName(String name);

}
