package javaApp.login.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;

import javaApp.login.model.LoginRequest;
import javaApp.login.model.LoginResponse;
import javaApp.login.model.WebResponse;
import javaApp.login.service.LoginService;

@RestController
public class LoginController {

    @Autowired
    private LoginService loginService;

    @PostMapping(path = "/api/login", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse loginResponse = loginService.login(request);
        return WebResponse.<LoginResponse>builder().data(loginResponse).build();
    }
}
