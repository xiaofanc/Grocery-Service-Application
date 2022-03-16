package edu.gatech.streamingwars.controller;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;
import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.validation.Valid;

import edu.gatech.streamingwars.auth.CookieAuthenticationFilter;
import edu.gatech.streamingwars.model.UserAccount;
import edu.gatech.streamingwars.logging.Response;
import edu.gatech.streamingwars.service.AuthenticationRpcService;
import edu.gatech.streamingwars.service.CustomerRpcService;
import edu.gatech.streamingwars.service.PilotRpcService;
import edu.gatech.streamingwars.service.UserRpcService;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.annotation.AuthenticationPrincipal;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class AuthenticationController {

    private final UserRpcService userRpcService;
    private final CustomerRpcService customerRpcService;
    private final PilotRpcService pilotRpcService;

    private final AuthenticationRpcService authenticationRpcService;

    public AuthenticationController(UserRpcService userRpcService, AuthenticationRpcService authenticationRpcService,
                                    CustomerRpcService customerRpcService, PilotRpcService pilotRpcService) {
        this.userRpcService = userRpcService;
        this.customerRpcService = customerRpcService;
        this.pilotRpcService = pilotRpcService;
        this.authenticationRpcService = authenticationRpcService;
    }

    /**
     * Signin API
     * @param userAccount
     * @param servletResponse
     * @return
     */
    @PostMapping("/signin")
    public Response signIn(@AuthenticationPrincipal UserAccount userAccount,
                                          HttpServletResponse servletResponse) {

        Cookie authCookie = new Cookie(CookieAuthenticationFilter.COOKIE_NAME, authenticationRpcService.createToken(userAccount));
        authCookie.setHttpOnly(true);
        authCookie.setSecure(true);
        authCookie.setMaxAge((int) Duration.of(1, ChronoUnit.DAYS).toSeconds());
        authCookie.setPath("/");

        servletResponse.addCookie(authCookie);
        userRpcService.signIn(userAccount);

        return new Response(ResponseEntity.ok(userAccount));
    }

    /**
     * Signup API
     * @param userAccount
     * @return
     */
    @PostMapping("/signup")
    public Response signUp(@RequestBody @Valid UserAccount userAccount) {
        UserAccount createdUserAccount = userRpcService.signUp(userAccount);
        if (userAccount.getUserRole().equals("customer")) {
            customerRpcService.addCustomer(userAccount);
        }
        return new Response(ResponseEntity.ok(createdUserAccount));
    }

    /**
     * Signout API
     * @param userAccount
     * @return
     */
    @PostMapping("/signout")
    public Response signOut(@AuthenticationPrincipal UserAccount userAccount) {
        SecurityContextHolder.clearContext();
        userRpcService.signOut(userAccount);
        return new Response(ResponseEntity.ok().build());
    }

    /**
     * Search user API
     * @param userId
     * @return
     */
    @GetMapping("/search")
    public Response searchUsers(@RequestParam(value = "userId") Long userId) {
        return new Response(ResponseEntity.ok(userRpcService.searchUser(userId)));
    }

    /**
     * Get all users API
     * @return
     */
    @GetMapping("/user/all")
    public Response displayUsers() {
        List<UserAccount> userAccounts = userRpcService.displayUsers();
        return new Response(ResponseEntity.ok(userAccounts));
    }

    /**
     * Count all online users
     * @return
     */
    @GetMapping("/count/online")
    public Response countRegisteredUsers() {
        int count = userRpcService.countOnlineUser();
        return new Response(ResponseEntity.ok(count));
    }

}
