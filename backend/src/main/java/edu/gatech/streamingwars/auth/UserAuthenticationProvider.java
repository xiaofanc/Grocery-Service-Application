package edu.gatech.streamingwars.auth;

import java.util.Collections;

import edu.gatech.streamingwars.model.UserAccount;
import edu.gatech.streamingwars.service.AuthenticationRpcService;
import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.web.authentication.preauth.PreAuthenticatedAuthenticationToken;
import org.springframework.stereotype.Component;

@Component
public class UserAuthenticationProvider implements AuthenticationProvider {

    private final AuthenticationRpcService authenticationRpcService;

    public UserAuthenticationProvider(AuthenticationRpcService authenticationRpcService) {
        this.authenticationRpcService = authenticationRpcService;
    }

    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        UserAccount userAccount = null;
        if (authentication instanceof UsernamePasswordAuthenticationToken) {
            // authentication by username and password
            userAccount = authenticationRpcService.authenticate(
                    new UserAccount((String) authentication.getPrincipal(), (char[]) authentication.getCredentials()));
        } else if (authentication instanceof PreAuthenticatedAuthenticationToken) {
            // authentication by cookie
            userAccount = authenticationRpcService.findByToken((String) authentication.getPrincipal());
        }

        if (userAccount == null) {
            return null;
        }

        return new UsernamePasswordAuthenticationToken(userAccount, null, Collections.emptyList());
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return true;
    }
}
