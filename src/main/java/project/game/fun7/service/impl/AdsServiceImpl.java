package project.game.fun7.service.impl;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.stereotype.Service;
import org.springframework.web.client.HttpStatusCodeException;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;
import project.game.fun7.model.dto.services.AdsResponse;
import project.game.fun7.service.AdsService;

import java.net.URI;
import java.util.Objects;


@Service
public class AdsServiceImpl implements AdsService {

    private static final Logger log = LoggerFactory.getLogger(AdsServiceImpl.class);

    @Value("${ads.auth.username}")
    private String username;

    @Value("${ads.auth.pass}")
    private String password;

    @Value("${ads.service.url}")
    private String url;

    @Override
    public boolean isEnabled(String countryCode) {

        log.info("Checking if ads service is enabled...");

        HttpHeaders headers = new HttpHeaders();
        headers.setBasicAuth(username, password);
        headers.set(HttpHeaders.ACCEPT, MediaType.APPLICATION_JSON_VALUE);

        HttpEntity<String> request = new HttpEntity<>(headers);

        URI uri = UriComponentsBuilder
                .fromUri(URI.create(url))
                .queryParam("countryCode", countryCode)
                .build()
                .toUri();

        try {
            ResponseEntity<AdsResponse> response = new RestTemplate().exchange(uri, HttpMethod.GET, request, AdsResponse.class);

            String responseString = Objects.requireNonNull(response.getBody()).getAds();

            return responseString.equals("sure, why not!");

        } catch (HttpStatusCodeException e) {

            String status = switch (e.getStatusCode().value()) {
                case 401 -> "Invalid credentials";
                case 400 -> "Missing mandatory parameters";
                case 500 -> "Server is temporarily not available";
                default -> "Ads service is currently unavailable";
            };

            log.error("Error occurred when making request to ads service with status: " + e.getStatusCode() + " " + status);
        }
        return false;
    }
}
