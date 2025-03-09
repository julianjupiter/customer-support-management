package com.julianjupiter.csm.repository;

import com.julianjupiter.csm.security.SecurityUtil;
import com.julianjupiter.csm.security.TokenDto;
import com.julianjupiter.csm.util.Constants;
import com.julianjupiter.csm.util.HttpClientBody;
import com.julianjupiter.csm.util.JsonBodyHandler;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;
import java.util.Map;

/**
 * @author Julian Jupiter
 */
public class LoginRepository {
    private static final Logger LOGGER = LoggerFactory.getLogger(LoginRepository.class);
    private final HttpClient httpClient;

    public LoginRepository() {
        this.httpClient = HttpClient.newHttpClient();
    }

    public void login(String username, String password) {
        var uri = URI.create(Constants.CSM_BASE_URL + "/v1/auth/login");
        var usernamePassword = Map.of(
                "username", username,
                "password", password
        );
        var httpRequest = HttpRequest.newBuilder(uri)
                .header("Content-Type", "application/x-www-form-urlencoded")
                .POST(HttpClientBody.ofForm(usernamePassword))
                .build();
        try {
            HttpResponse<TokenDto> response = this.httpClient.send(httpRequest, JsonBodyHandler.of(TokenDto.class));
            int statusCode = response.statusCode();
            if (statusCode == 200) {
                TokenDto tokenDto = response.body();
                if (tokenDto != null) {
                    SecurityUtil.setToken(tokenDto);
                    SecurityUtil.show();
                }
            } else {
                LOGGER.error("Error login, HTTP status: {}", statusCode);
            }
        } catch (IOException | InterruptedException e) {
            LOGGER.error(e.getMessage());
        }
    }
}
