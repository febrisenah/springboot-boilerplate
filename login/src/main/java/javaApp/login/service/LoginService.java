package javaApp.login.service;

import lombok.extern.slf4j.Slf4j;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.server.ResponseStatusException;

import javaApp.entity.Role;
import javaApp.entity.User;
import javaApp.login.model.LoginRequest;
import javaApp.login.model.LoginResponse;
import javaApp.login.repository.LoginRepository;
import javaApp.login.security.BCrypt;
import javaApp.security.JwtService;
import javaApp.users.repository.RoleRepository;
import javaApp.users.service.ValidationService;

@Service
@Slf4j
public class LoginService {
    @Autowired
    private JwtService jwtService;

    @Autowired
    private LoginRepository loginRepository;

    @Autowired
    private RoleRepository roleRepository;

    @Autowired
    private ValidationService validationService;

    @Transactional
    public LoginResponse login(LoginRequest request) {
        validationService.validate(request);
        User user = loginRepository.findByEmail(request.getEmail()).orElseThrow(
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
            loginRepository.update(userUpdate);
            return LoginResponse.builder()
                    .accessToken(jwtService.generateTokenAccessToken(request.getEmail()))
                    .refreshToken(jwtService.generateTokenRefreshToken(request.getEmail()))
                    .build();
        } else {
            throw new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Username or password wrong");
        }
    }
}
