package project.game.fun7.web.jwt;

import com.auth0.jwt.JWT;
import com.auth0.jwt.algorithms.Algorithm;
import com.auth0.jwt.interfaces.DecodedJWT;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.web.authentication.www.BasicAuthenticationFilter;

import java.io.IOException;
import java.nio.charset.Charset;
import java.time.Instant;
import java.util.ArrayList;
import java.util.Base64;
import java.util.HashMap;
import java.util.stream.Collectors;

public class JWTAuthorizationFilter extends BasicAuthenticationFilter{

    private final Logger log = LoggerFactory.getLogger(JWTAuthorizationFilter.class);
    private final String HEADER_AUTHORIZATION = "Authorization";
    private final String TOKEN_PREFIX = "Bearer ";

    private final ObjectMapper objectMapper;

    public JWTAuthorizationFilter(AuthenticationManager authManager) {
        super(authManager);
        objectMapper = new ObjectMapper();
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain chain) throws IOException, ServletException {

        String header = request.getHeader(HEADER_AUTHORIZATION);

        if (header == null || !header.startsWith(TOKEN_PREFIX)) {
            chain.doFilter(request, response);
            return;
        }

        UsernamePasswordAuthenticationToken authentication = getAuthentication(request);

        SecurityContextHolder.getContext().setAuthentication(authentication);
        chain.doFilter(request, response);
    }

    private UsernamePasswordAuthenticationToken getAuthentication(HttpServletRequest request) {
        String token = request.getHeader(HEADER_AUTHORIZATION);
        if (token != null) {
            DecodedJWT jwt = JWT.require(Algorithm.HMAC512((SecurityConstants.SECRET).getBytes()))
                    .build()
                    .verify(token.replace(TOKEN_PREFIX, ""));
            if (jwt.getExpiresAtAsInstant().isAfter(Instant.now())) {
                String username = jwt.getSubject();
                if (username != null) {
                    try {
                        String json = new String(Base64.getDecoder().decode(jwt.getPayload()), Charset.defaultCharset());
                        HashMap<?, ?> jsonMap = objectMapper.readValue(json, HashMap.class);
                        ArrayList<?> authorities = (ArrayList<?>) jsonMap.get("authorities");
                        ArrayList<SimpleGrantedAuthority> authoritiesArrayList = authorities.stream().map(a -> new SimpleGrantedAuthority(a.toString())).collect(Collectors.toCollection(ArrayList::new));
                        return new UsernamePasswordAuthenticationToken(username, null, authoritiesArrayList);
                    } catch (Exception e) {
                        log.error(e.getLocalizedMessage(), e);
                    }
                }
                return null;
            }
            log.debug("Token expired");
            return null;
        }
        return null;
    }

}
