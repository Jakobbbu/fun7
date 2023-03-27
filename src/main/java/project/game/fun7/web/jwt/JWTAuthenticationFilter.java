package project.game.fun7.web.jwt;

import com.auth0.jwt.JWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.AuthenticationException;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.security.web.authentication.UsernamePasswordAuthenticationFilter;
import project.game.fun7.model.dto.users.UserLoginDTO;

import java.io.IOException;
import java.util.ArrayList;
import java.util.Collection;
import java.util.Date;
import java.util.stream.Collectors;

import static com.auth0.jwt.algorithms.Algorithm.HMAC512;

public class JWTAuthenticationFilter extends UsernamePasswordAuthenticationFilter {

    private final Logger log = LoggerFactory.getLogger(JWTAuthenticationFilter.class);

    private final String HEADER_AUTHORIZATION = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    private final AuthenticationManager authenticationManager;
    private final ObjectMapper objectMapper;

    public JWTAuthenticationFilter(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
        objectMapper = new ObjectMapper();
    }

    @Override
    public Authentication attemptAuthentication(HttpServletRequest request, HttpServletResponse response) throws AuthenticationException {
        try {
            UserLoginDTO credentials = objectMapper.readValue(request.getInputStream(), UserLoginDTO.class);
            return authenticationManager.authenticate(
                    new UsernamePasswordAuthenticationToken(
                            credentials.getUsername(),
                            credentials.getPassword(),
                            new ArrayList<>())
            );
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    @Override
    protected void successfulAuthentication(HttpServletRequest request, HttpServletResponse response, FilterChain chain, Authentication auth) {
        Fun7User fun7User = (Fun7User) auth.getPrincipal();
        String token = JWT.create()
                .withSubject(fun7User.getUsername())
                .withClaim("authorities", getAuthNames(fun7User.getAuthorities()))
                .withExpiresAt(new Date(System.currentTimeMillis() + SecurityConstants.EXPIRATION_TIME))
                .sign(HMAC512((SecurityConstants.SECRET).getBytes()));
        log.info("created token: {}", token);
        response.addHeader(HEADER_AUTHORIZATION, TOKEN_PREFIX + token);
    }

    private ArrayList<String> getAuthNames(Collection<? extends GrantedAuthority> authorities) {

        return authorities.stream().map(GrantedAuthority::toString).collect(Collectors.toCollection(ArrayList::new));

        /*ArrayList<String> authNames = new ArrayList<>();
        for(GrantedAuthority grantedAuthority : authorities) {
            authNames.add(grantedAuthority.getAuthority());
        }
        return authNames;*/
    }

}
