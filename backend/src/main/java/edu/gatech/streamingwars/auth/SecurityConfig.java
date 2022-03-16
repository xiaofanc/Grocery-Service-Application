package edu.gatech.streamingwars.auth;

import org.springframework.http.HttpMethod;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configuration.EnableWebSecurity;
import org.springframework.security.config.annotation.web.configuration.WebSecurityConfigurerAdapter;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

@EnableWebSecurity
public class SecurityConfig extends WebSecurityConfigurerAdapter {

    private final UserAuthenticationEntryPoint userAuthenticationEntryPoint;

    public SecurityConfig(UserAuthenticationEntryPoint userAuthenticationEntryPoint,
                          UserAuthenticationProvider userAuthenticationProvider) {
        this.userAuthenticationEntryPoint = userAuthenticationEntryPoint;
    }

    @Override
    protected void configure(HttpSecurity http) throws Exception {
        http
                .exceptionHandling().authenticationEntryPoint(userAuthenticationEntryPoint)
                .and()
                .formLogin()
                .loginPage("/login")
                .permitAll().and()
                .addFilterBefore(new UsernamePasswordAuthFilter(), BasicAuthenticationFilter.class)
                .addFilterBefore(new CookieAuthenticationFilter(), UsernamePasswordAuthFilter.class)
                .csrf().disable()
                .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS)
                .and().logout().logoutUrl("/auth/signout").logoutSuccessUrl("/login").deleteCookies(CookieAuthenticationFilter.COOKIE_NAME).invalidateHttpSession(true)
                .and()
                .authorizeRequests()
                .antMatchers(HttpMethod.POST,"/auth/signin", "/auth/signup").permitAll()
//                .antMatchers(HttpMethod.GET,"/**").permitAll()
                .antMatchers("/", "/login", "/*.css").permitAll()
                .anyRequest().authenticated();
    }

}
