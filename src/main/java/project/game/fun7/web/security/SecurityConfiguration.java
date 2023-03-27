package project.game.fun7.web.security;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpMethod;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.config.annotation.web.builders.HttpSecurity;
import org.springframework.security.config.annotation.web.configurers.AbstractHttpConfigurer;
import org.springframework.security.config.http.SessionCreationPolicy;
import org.springframework.security.web.SecurityFilterChain;
import org.springframework.security.web.util.matcher.AntPathRequestMatcher;
import project.game.fun7.web.jwt.JWTAuthenticationFilter;
import project.game.fun7.web.jwt.JWTAuthorizationFilter;

@Configuration
public class SecurityConfiguration {

    private final AuthenticationManager authenticationManager;

    public SecurityConfiguration(AuthenticationManager authenticationManager) {
        this.authenticationManager = authenticationManager;
    }

    @Bean
    public SecurityFilterChain securityFilterChain(HttpSecurity http) throws Exception {
        http.authorizeHttpRequests(auth -> {
                            try {
                                auth
                                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.POST, "/login")).permitAll()
                                        /*.requestMatchers(AntPathRequestMatcher.antMatcher("/h2-console/**")).permitAll()*/
                                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/v3/api-docs/**")).permitAll()
                                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/swagger-ui/**")).permitAll()
                                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/swagger-ui.html")).permitAll()
                                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.GET, "/api-docs.yaml")).permitAll()
                                        .requestMatchers(AntPathRequestMatcher.antMatcher("/services/**")).hasAnyRole("USER", "ADMIN")
                                        .requestMatchers(AntPathRequestMatcher.antMatcher("/administration/**")).hasRole("ADMIN")
                                        .requestMatchers(AntPathRequestMatcher.antMatcher(HttpMethod.DELETE)).hasRole("ADMIN")
                                        .anyRequest().authenticated()
                                        .and()
                                        .addFilter(new JWTAuthenticationFilter(authenticationManager))
                                        .addFilter(new JWTAuthorizationFilter(authenticationManager))
                                        .sessionManagement().sessionCreationPolicy(SessionCreationPolicy.STATELESS);
                            } catch (Exception e) {
                                throw new RuntimeException(e);
                            }
                        }
                )
                .headers(headers -> headers.frameOptions().disable())
                .csrf(AbstractHttpConfigurer::disable/*ignoringRequestMatchers(
                        AntPathRequestMatcher.antMatcher("/h2-console/**"),
                        AntPathRequestMatcher.antMatcher("/login/**"))*/
                );
        return http.build();
    }
}
