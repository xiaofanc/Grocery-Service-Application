package edu.gatech.streamingwars.repo;

import edu.gatech.streamingwars.model.Drone;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface DroneRepo extends JpaRepository<Drone, Long> {

    Optional<Drone> findByDroneId(String droneId);
}
