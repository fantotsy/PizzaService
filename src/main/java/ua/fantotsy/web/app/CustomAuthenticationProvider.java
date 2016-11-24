package ua.fantotsy.web.app;


import org.springframework.security.authentication.AuthenticationProvider;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Objects;

@Component
public class CustomAuthenticationProvider implements AuthenticationProvider {
    @Override
    public Authentication authenticate(Authentication authentication) throws AuthenticationException {
        String userName = authentication.getName();
        String password = authentication.getPrincipal().toString();
        if (Objects.equals(userName, "user") && Objects.equals(password, "password")) {
            Authentication authentication1 =
                    new UsernamePasswordAuthenticationToken(userName, "",
                            Arrays.asList(new SimpleGrantedAuthority("USER_ROLE")));

            return authentication;
        }
        return null;
    }

    @Override
    public boolean supports(Class<?> type) {
        return type.equals(UsernamePasswordAuthenticationToken.class);
    }
}