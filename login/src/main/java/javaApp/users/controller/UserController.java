package javaApp.users.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javaApp.entity.User;
import javaApp.users.model.ProfileUserRequest;
import javaApp.users.model.RegisterUserRequest;
import javaApp.users.model.UpdateUserRequest;
import javaApp.users.model.UserResponse;
import javaApp.users.model.WebResponse;
import javaApp.users.service.UserService;

import java.util.List;
import java.util.UUID;

@RestController
public class UserController {

    @Autowired
    private UserService userService;

    @PostMapping(value = "/api/profile", consumes = "multipart/form-data")
    public WebResponse<String> handleFileUpload(@ModelAttribute ProfileUserRequest request) {
        userService.profile(request);
        return  WebResponse.<String>builder().data("OK").build();
    }

    @PostMapping(path = "/api/users", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> addUser(User user, @RequestBody RegisterUserRequest request) {
        userService.addUser(request);
        return WebResponse.<String>builder().data("OK").build();
    }

    @GetMapping(path = "/api/users", produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<List<UserResponse>> getAllUsers(User user) {
        List<UserResponse> userResponses = userService.getAllUsers(user);
        return WebResponse.<List<UserResponse>>builder().data(userResponses).build();
    }

    @PatchMapping(path = "/api/users/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<UserResponse> update(
            User user,@PathVariable("userId") UUID userId, @RequestBody UpdateUserRequest request) {
        UserResponse userResponse = userService.update(userId, request);
        return WebResponse.<UserResponse>builder().data(userResponse).build();
    }

    @DeleteMapping(path = "/api/users/{userId}", consumes = MediaType.APPLICATION_JSON_VALUE, produces = MediaType.APPLICATION_JSON_VALUE)
    public WebResponse<String> delete(
            User user,@PathVariable("userId") UUID userId, @RequestBody UpdateUserRequest request) {
        userService.delete(userId, request);
        return WebResponse.<String>builder().data("ok").build();
    }
}
