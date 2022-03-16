package edu.gatech.streamingwars.repo;

import edu.gatech.streamingwars.model.UserAccount;
import org.springframework.data.jpa.repository.JpaRepository;

import java.util.Optional;

public interface UserRepo extends JpaRepository<UserAccount, String> {
    Optional<UserAccount> findUserById(Long userId);
    Optional<UserAccount> findUserByUserName(String userName);
}
