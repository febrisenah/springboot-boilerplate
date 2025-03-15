package javaApp.login.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaApp.entity.User;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface LoginRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    default User update(User user) {
        return save(user);
    }
}
