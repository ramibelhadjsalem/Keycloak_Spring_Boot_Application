package com.KeycloakApp.KeycloakApp;

import jakarta.validation.constraints.NotNull;
import lombok.Getter;

@Getter
public class LoginRequest {
    @NotNull
    String username;
    @NotNull
    String password;
}