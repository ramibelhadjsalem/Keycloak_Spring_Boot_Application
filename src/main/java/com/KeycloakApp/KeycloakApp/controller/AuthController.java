package com.KeycloakApp.KeycloakApp.controller;


import com.KeycloakApp.KeycloakApp.CreateUserRequest;
import com.KeycloakApp.KeycloakApp.KeycloakAdminClientService;
import com.KeycloakApp.KeycloakApp.LoginRequest;
import com.KeycloakApp.KeycloakApp.config.KeycloakProvider;
import jakarta.validation.constraints.NotNull;
import jakarta.ws.rs.core.Response;
import org.keycloak.OAuth2Constants;
import org.keycloak.admin.client.Keycloak;
import org.keycloak.admin.client.KeycloakBuilder;
import org.keycloak.representations.AccessTokenResponse;
import org.slf4j.Logger;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/api/auth")
public class AuthController {
    private final KeycloakAdminClientService kcAdminClient;
    private final KeycloakProvider kcProvider;

    private static final Logger LOG = org.slf4j.LoggerFactory.getLogger(AuthController.class);

    public AuthController(KeycloakAdminClientService kcAdminClient, KeycloakProvider kcProvider) {
        this.kcAdminClient = kcAdminClient;
        this.kcProvider = kcProvider;
    }

    @PostMapping(value = "/create")
    public ResponseEntity<?> createUser(@RequestBody CreateUserRequest user) {
        Response createdResponse = kcAdminClient.createKeycloakUser(user);
        return ResponseEntity.status(createdResponse.getStatus()).build();

    }

    @PostMapping("/login")
    public ResponseEntity<?> login(@NotNull @RequestBody LoginRequest loginRequest) {
        Keycloak keycloak = kcProvider.newKeycloakBuilderWithPasswordCredentials(loginRequest.getUsername(), loginRequest.getPassword()).build();

        Object accessTokenResponse = null;
        try {
            accessTokenResponse = keycloak.tokenManager().getAccessToken();
            return ResponseEntity.status(HttpStatus.OK).body(accessTokenResponse);
        } catch (Exception ex) {
            LOG.warn("invalid account. User probably hasn't verified email.", ex);
            return ResponseEntity.status(HttpStatus.FORBIDDEN).body(accessTokenResponse);
        }

//        Keycloak keycloak= KeycloakBuilder.builder() //
//                .realm("MySuperApplicationRealm") //
//                .serverUrl("http://localhost:8080")//
//                .clientId("my-super-client") //
//                .clientSecret("d0bHHWcHogxvmjkiBg4qoot7l5iTgdVT")
//                .grantType(OAuth2Constants.PASSWORD)//
//                .username("user1@gmail.com")//
//                .password("password")
//                .build();
//
//        return  ResponseEntity.ok(keycloak.tokenManager().getAccessToken().getToken());

    }
}
