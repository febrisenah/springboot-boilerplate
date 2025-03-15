package javaApp.auth.service;

import lombok.extern.slf4j.Slf4j;

import java.util.UUID;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javaApp.auth.model.LoginRequest;
import javaApp.auth.model.LoginResponse;
import javaApp.auth.model.RegisterRequest;
import javaApp.auth.repository.UserAuthRepository;
import javaApp.auth.security.BCrypt;
import javaApp.entity.Role;
import javaApp.entity.User;
import javaApp.security.JwtService;
import javaApp.users.repository.RoleRepository;
import javaApp.users.service.ValidationService;

@Service
@Slf4j
public class AuthService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private UserAuthRepository userRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public void register(RegisterRequest request){
        validationService.validate(request);
        User user = new User();
        Role role = roleRepository.findByRoleName(request.getRole())
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
        userRepository.create(user);
    }

    @Transactional
    public LoginResponse login(LoginRequest request) {
        validationService.validate(request);
        User user = userRepository.findByEmail(request.getEmail()).orElseThrow(
                () -> new ResponseStatusException(HttpStatus.BAD_REQUEST, "User not found"));
        if (BCrypt.checkpw(request.getPassword(), user.getPassword())) {
            User userUpdate = new User();
            Role role = roleRepository.findByRoleName("Admin")
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
            userUpdate.setId(user.getId());
            userUpdate.setRefreshtoken(jwtService.generateTokenAccessToken(request.getEmail()));
            userUpdate.setEmail(user.getEmail());
            userUpdate.setPassword(user.getPassword());
            userUpdate.setRoleId(user.getRoleId());
            userUpdate.setIsActive(user.getIsActive());
            userUpdate.setIsLogin(user.getIsLogin());
            userUpdate.setRole(role);
            userRepository.update(userUpdate);
            return LoginResponse.builder()
                    .accessToken(jwtService.generateTokenAccessToken(request.getEmail()))
                    .refreshToken(jwtService.generateTokenRefreshToken(request.getEmail()))
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong");
        }
    }
}
