package mshcherba.ecommerce.payu;

import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;

import javax.print.attribute.standard.Media;

public class PayU {
    RestTemplate http;
    PayUCredentials credentials;

    public PayU(RestTemplate http,PayUCredentials credentials ) {
        this.http = http;
        this.credentials = credentials;
    }

    public OrderCreateResponse handle(OrderCreateRequest request) {
        var url = getUrl("/api/v2_1/orders");

        HttpHeaders headers = new HttpHeaders();
        headers.add("Content-Type", "application/json");
        headers.add("Authorization", String.format("Bearer %s", getToken()));

        HttpEntity<OrderCreateRequest> headerAwareRequest = new HttpEntity<>(request, headers);

        ResponseEntity<OrderCreateResponse> response = http.postForEntity(url, headerAwareRequest, OrderCreateResponse.class);

        return response.getBody();
    }

    private String getToken() {
        String body = String.format(
                "grant_type=client_credentials&client_id=%s&client_secret=%s",
                credentials.getClientId(),
                credentials.getClientSecret()
        );
        var url = getUrl("/pl/standard/user/oauth/authorize");

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.APPLICATION_FORM_URLENCODED);

        HttpEntity<String> request = new HttpEntity<>(body, headers);
        ResponseEntity<AccessTokenResponse> response = http.postForEntity(url, request, AccessTokenResponse.class);


        return response.getBody().getAccessToken();

    }
    private String getUrl(String path) {
        return String.format("%s%s", credentials.getBaseUrl(), path);
    }
}