package javaApp.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaApp.entity.User;
import javaApp.security.BCrypt;
import javaApp.users.model.UpdateUserRequest;

import java.util.List;
import java.util.Objects;
import java.util.Optional;
import java.util.UUID;

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

    default User create(User user){
        return save(user);
    }

    default User updateUser(User user) {
        return save(user);
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

    default List<User> getAllUserResponses() {
        return findAll();
    }
}
