package edu.gatech.streamingwars.repo;

import edu.gatech.streamingwars.model.Pilot;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface PilotRepo extends JpaRepository<Pilot, Long> {

    Optional<Pilot> findPilotByAccount(String account);
}
