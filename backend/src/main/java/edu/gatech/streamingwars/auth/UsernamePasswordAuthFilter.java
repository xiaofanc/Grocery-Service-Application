package edu.gatech.streamingwars.auth;

import java.io.IOException;
import javax.servlet.FilterChain;
import javax.servlet.ServletException;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;

import com.fasterxml.jackson.databind.ObjectMapper;
import edu.gatech.streamingwars.model.UserAccount;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;

public class UsernamePasswordAuthFilter extends OncePerRequestFilter {

    private static final ObjectMapper MAPPER = new ObjectMapper();

    @Override
    protected void doFilterInternal(
            HttpServletRequest httpServletRequest,
            HttpServletResponse httpServletResponse,
            FilterChain filterChain) throws ServletException, IOException {

        if ("/auth/signin".equals(httpServletRequest.getServletPath())
                && HttpMethod.POST.matches(httpServletRequest.getMethod())) {
            UserAccount userAccount = MAPPER.readValue(httpServletRequest.getInputStream(), UserAccount.class);

            SecurityContextHolder.getContext().setAuthentication(
                    new UsernamePasswordAuthenticationToken(userAccount.getUserName(), userAccount.getPassword()));
        }

        filterChain.doFilter(httpServletRequest, httpServletResponse);
    }
}
