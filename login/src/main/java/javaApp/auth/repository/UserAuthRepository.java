package javaApp.auth.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import javaApp.entity.User;
import java.util.Optional;
import java.util.UUID;

@Repository
public interface UserAuthRepository extends JpaRepository<User, UUID> {
    Optional<User> findByEmail(String email);

    default User create(User user){
        return save(user);
    }

    default User update(User user) {
        return save(user);
    }
}
