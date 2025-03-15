package javaApp.auth.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javaApp.auth.model.LoginRequest;
import javaApp.auth.model.LoginResponse;
import javaApp.auth.model.RegisterRequest;
import javaApp.auth.model.WebResponse;
import javaApp.auth.service.AuthService;

@RestController
public class AuthController {

    @Autowired
    private AuthService loginService;

    @PostMapping(path = "/api/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = loginService.login(request);
        return WebResponse.<LoginResponse>builder().data(loginResponse).build();
    }

    @PostMapping(path = "/api/register", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> register(@RequestBody RegisterRequest request) {
        loginService.register(request);
        return WebResponse.<String>builder().data("ok").build();
    }
}
