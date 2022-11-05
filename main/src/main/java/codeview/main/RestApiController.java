package codeview.main;

import lombok.RequiredArgsConstructor;
import org.springframework.core.ParameterizedTypeReference;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpMethod;
import org.springframework.http.ResponseEntity;
import org.springframework.security.oauth2.client.OAuth2AuthorizedClient;
import org.springframework.security.oauth2.client.annotation.RegisteredOAuth2AuthorizedClient;
import org.springframework.security.oauth2.core.OAuth2AccessToken;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import java.util.List;

@RestController
@RequiredArgsConstructor
public class RestApiController {

    private final RestTemplate restTemplate;

    @GetMapping("/token")
    public OAuth2AccessToken token(@RegisteredOAuth2AuthorizedClient("keyCloak") OAuth2AuthorizedClient oAuth2AuthorizedClient) {
        return oAuth2AuthorizedClient.getAccessToken();
    }

    @GetMapping("/photoes")
    public List<Photo> photos(AccessToken accessToken) {

        HttpHeaders headers = new HttpHeaders();
        headers.add("Authorization", "Bearer " + accessToken.getToken());
        HttpEntity<?> entity = new HttpEntity<>(headers);
        String url = "http://localhost:8082/photos";

        ResponseEntity<List<Photo>> response = restTemplate.exchange(url, HttpMethod.GET, entity, new ParameterizedTypeReference<>() {
        });

        return response.getBody();
    }
}
