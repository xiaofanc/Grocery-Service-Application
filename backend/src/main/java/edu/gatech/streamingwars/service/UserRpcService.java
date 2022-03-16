package edu.gatech.streamingwars.service;

import java.util.List;

import edu.gatech.streamingwars.model.UserAccount;
import edu.gatech.streamingwars.repo.UserRepo;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;

@Service
public class UserRpcService {

    private final UserRepo userRepo;

    @Autowired
    public UserRpcService(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    /**
     * search user by id
     * @param userId
     * @return
     */
    public UserAccount searchUser(Long userId) {
        return userRepo.findUserById(userId).orElseThrow(
                () -> new RuntimeException("User " + userId + " not found")
        );
    }

    public UserAccount signUp(UserAccount userAccount) {
        return userRepo.save(userAccount);
    }

    public void signIn(UserAccount userAccount) {
        userAccount.setSignIn(true);
        userRepo.save(userAccount);
    }

    public void signOut(UserAccount userAccount) {
        userAccount.setSignIn(false);
        userRepo.save(userAccount);
    }

    /**
     * display all users
     * @return list of user
     */
    public List<UserAccount> displayUsers() {
        return userRepo.findAll(Sort.by(Sort.Direction.ASC, "userName"));
    }

    /**
     * count online users
     * @return int
     */
    public int countOnlineUser() {
        int count = 0;
        List<UserAccount> userAccounts = userRepo.findAll();
        for (UserAccount u: userAccounts) {
            if (u.isSignIn()) {
                count += 1;
            }
        }
        return count;
    }
}
