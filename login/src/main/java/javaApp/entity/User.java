package javaApp.entity;

import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;
import java.util.UUID;

@Entity
@Table(name = "\"user\"")
@Getter
@Setter
public class User {
    @Id
    @Column(name = "id", nullable = false)
    private UUID id;
    private String email;
    private String password;
    private String refreshtoken;
    private Boolean isActive;
    private Boolean isLogin;

    @Transient
    private UUID roleId;

    @OneToOne
    @JoinColumn(name = "role_id", referencedColumnName = "id")
    private Role role;

    @Override
    public String toString() {
        return "User{" +
                "id=" + id +
                ", email='" + email + '\'' +
                ", password='" + password + '\'' +
                ", isActive=" + isActive +
                ", isLogin=" + isLogin +
                ", role=" + (role != null ? role : "null") +
                '}';
    }
}
