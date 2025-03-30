package javaApp.users.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.server.ResponseStatusException;

import javaApp.entity.Role;
import javaApp.entity.User;
import javaApp.security.BCrypt;
import javaApp.users.model.ProfileUserRequest;
import javaApp.users.model.RegisterUserRequest;
import javaApp.users.model.UpdateUserRequest;
import javaApp.users.model.UserResponse;
import javaApp.repository.RoleRepository;
import javaApp.repository.UserRepository;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.List;
import java.util.UUID;
import java.util.stream.Collectors;

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
    public void profile(ProfileUserRequest request){
        try {
            validationService.validate(request);
            MultipartFile file = request.getFile();
            String folderPath = System.getProperty("user.dir") + "/uploads";
            Path folder = Paths.get(folderPath);
            if (!Files.exists(folder)) {
                Files.createDirectories(folder);
            }
            Path filePath = folder.resolve(file.getOriginalFilename());
            file.transferTo(filePath.toFile());
        } catch (IOException e) {
            throw new RuntimeException("Failed to save file", e);
        } catch (Exception e){
            throw new RuntimeException("Error", e);
        }
    }

    @Transactional
    public void delete(UUID userId, UpdateUserRequest request) {
        try{
            validationService.validate(request);
            userRepository.deleteById(userId);
        }catch(Exception e){
            throw new RuntimeException("Error", e);
        }
    }

    @Transactional
    public UserResponse update(UUID userId, UpdateUserRequest request) {
        try {     
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
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }

    @Transactional
    public void addUser(RegisterUserRequest request) {
        try {            
            validationService.validate(request);
            User user = new User();
            Role role = roleRepository.findByRoleName(request.getRole())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Role not found"));
            userRepository.findByEmail(request.getEmail())
                    .orElseThrow(() -> new ResponseStatusException(HttpStatus.NOT_FOUND, "Email Already Exist"));
            user.setId(UUID.randomUUID());
            user.setEmail(request.getEmail());
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
            user.setRole(role);
            user.setIsActive(true);
            user.setIsLogin(true);
            userRepository.createAndSaveUser(user);
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }

    public List<UserResponse> getAllUsers(User user) {
        try {
            return userRepository.getAllUserResponses().stream()
                .map(users -> UserResponse.builder()
                        .role(UserResponse.RoleDetails.builder()
                                .id(users.getRole().getId())
                                .roleName(users.getRole().getRoleName())
                                .build())
                        .email(users.getEmail())
                        .password(users.getPassword())
                        .isActive(users.getIsActive())
                        .isLogin(users.getIsLogin())
                        .id(users.getId())
                        .build())
                .collect(Collectors.toList());
        } catch (Exception e) {
            throw new RuntimeException("Error", e);
        }
    }
}
