package javaApp.users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javaApp.entity.Role;
import javaApp.entity.User;
import javaApp.security.BCrypt;
import javaApp.users.model.RegisterUserRequest;
import javaApp.users.model.UpdateUserRequest;
import javaApp.users.model.UserResponse;
import javaApp.users.repository.RoleRepository;
import javaApp.users.repository.UserRepository;

import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

@Service
@Slf4j
public class UserService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ValidationService validationService;

     @Transactional
    public void delete(UUID userId, UpdateUserRequest request) {
        validationService.validate(request);
        userRepository.deleteById(userId);
    }

    @Transactional
    public UserResponse update(UUID userId, UpdateUserRequest request) {
        validationService.validate(request);
        User user = userRepository.getUserById(userId);
        userRepository.update(userId, request);
        return UserResponse.builder()
                .email(user.getEmail())
                .password(user.getPassword())
                .isActive(true)
                .isLogin(true)
                .role(
                        UserResponse.RoleDetails.builder()
                                .id(user.getRole().getId())
                                .roleName(user.getRole()
                                        .getRoleName())
                                .build())
                .build();
    }

    @Transactional
    public void addUser(RegisterUserRequest request) {
        validationService.validate(request);
        User user = new User();
        Role role = roleRepository.findByRoleName("Admin")
                .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
        userRepository.findByEmail(request.getEmail())
                .ifPresent(_ -> {
                    throw new ResponseStatusException(HttpStatus.BAD_REQUEST, "Email already exists");
                });
        user.setId(UUID.randomUUID());
        user.setEmail(request.getEmail());
        user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        user.setRole(role);
        user.setIsActive(true);
        user.setIsLogin(true);
        userRepository.createAndSaveUser(user);
    }

    public List<UserResponse> getUsers() {
        return new ArrayList<>();
    }

    public List<UserResponse> getAllUsers(User user) {
        System.out.println(user);
        return userRepository.getAllUserResponses();
    }
}
