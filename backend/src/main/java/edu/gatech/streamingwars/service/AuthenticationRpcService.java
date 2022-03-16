package edu.gatech.streamingwars.service;

import java.nio.CharBuffer;
import java.nio.charset.StandardCharsets;
import java.security.InvalidKeyException;
import java.security.NoSuchAlgorithmException;
import java.util.Base64;
import java.util.Objects;

import javax.crypto.Mac;
import javax.crypto.spec.SecretKeySpec;

import edu.gatech.streamingwars.model.UserAccount;
import edu.gatech.streamingwars.repo.UserRepo;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

@Service
public class AuthenticationRpcService {

    private final PasswordEncoder passwordEncoder;
    private final UserRepo userRepo;

    @Value("${security.jwt.token.secret-key:secret-key}")
    private String secretKey;

    public AuthenticationRpcService(PasswordEncoder passwordEncoder, UserRepo userRepo) {
        this.passwordEncoder = passwordEncoder;
        this.userRepo = userRepo;
    }

    /**
     * check authentication of user
     * @param userAccount
     * @return User
     */
    public UserAccount authenticate(UserAccount userAccount) {
        UserAccount authUserAccount = userRepo.findUserByUserName(userAccount.getUserName()).orElseThrow(
                () -> new RuntimeException("User " + userAccount.getUserName() + " not found"));
        String encodedMasterPassword = passwordEncoder.encode(CharBuffer.wrap(authUserAccount.getPassword()));
        if (passwordEncoder.matches(CharBuffer.wrap(userAccount.getPassword()), encodedMasterPassword)) {
            return authUserAccount;
        }
        throw new RuntimeException("Invalid password");
    }

    public String createToken(UserAccount userAccount) {
        return userAccount.getId() + "&" + userAccount.getUserName() + "&" + calculateHmac(userAccount);
    }

    /**
     * find user by token
     * @param token
     * @return user
     */
    public UserAccount findByToken(String token) {
        String[] parts = token.split("&");

        Long userId = Long.valueOf(parts[0]);
        String userName = parts[1];
        String hmac = parts[2];

        UserAccount userAccount = userRepo.findUserByUserName(userName).orElseThrow(
                () -> new RuntimeException("Invalid Cookie value"));

        if (!hmac.equals(calculateHmac(userAccount)) || userId != userAccount.getId()) {
            throw new RuntimeException("Invalid Cookie value");
        }

        return userAccount;
    }

    /**
     * Used for authorize user's login cookie
     * @param userAccount
     * @return
     */
    private String calculateHmac(UserAccount userAccount) {
        byte[] secretKeyBytes = Objects.requireNonNull(secretKey).getBytes(StandardCharsets.UTF_8);
        byte[] valueBytes = Objects.requireNonNull(userAccount.getId() + "&" + userAccount.getUserName()).getBytes(StandardCharsets.UTF_8);

        try {
            Mac mac = Mac.getInstance("HmacSHA512");
            SecretKeySpec secretKeySpec = new SecretKeySpec(secretKeyBytes, "HmacSHA512");
            mac.init(secretKeySpec);
            byte[] hmacBytes = mac.doFinal(valueBytes);
            return Base64.getEncoder().encodeToString(hmacBytes);

        } catch (NoSuchAlgorithmException | InvalidKeyException e) {
            throw new RuntimeException(e);
        }
    }
}
