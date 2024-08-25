package com.KeycloakApp.KeycloakApp.controller;


import lombok.Data;

import lombok.RequiredArgsConstructor;

@Data
@RequiredArgsConstructor
public class RegistrationRequest {
    private String username;
    private String email;
    private String password;
}
