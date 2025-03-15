package javaApp.users.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaApp.entity.User;
import javaApp.security.BCrypt;
import javaApp.users.model.UpdateUserRequest;
import javaApp.users.model.UserResponse;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;
import java.util.stream.Collectors;

@Repository
public interface UserRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    Optional<User> findAllById(UUID id);

    default User getUserById(UUID id) {
        return findAllById(id)
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + id));
    }

    default User getUserByEmail(String email) {
        return findByEmail(email)
                .orElseThrow(() -> new IllegalArgumentException("User not found for email: " + email));
    }

    default User update(UUID userId, UpdateUserRequest request) {
        User user = findAllById(userId)
                .orElseThrow(() -> new IllegalArgumentException("User not found for id: " + userId));

        if (Objects.nonNull(request.getEmail())) {
            user.setEmail(request.getEmail());
        }

        if (Objects.nonNull(request.getPassword())) {
            user.setPassword(BCrypt.hashpw(request.getPassword(), BCrypt.gensalt()));
        }

        return save(user);
    }

    default User createAndSaveUser(User user) {
        return save(user);
    }

    default List<UserResponse> getAllUserResponses() {
        return findAll().stream()
                .map(user -> UserResponse.builder()
                        .role(
                                UserResponse.RoleDetails.builder()
                                        .id(user.getRole().getId())
                                        .roleName(user.getRole().getRoleName())
                                        .build())
                        .email(user.getEmail())
                        .password(user.getPassword())
                        .isActive(user.getIsActive())
                        .isLogin(user.getIsLogin())
                        .id(user.getId())
                        .build())
                .collect(Collectors.toList());
    }
}
